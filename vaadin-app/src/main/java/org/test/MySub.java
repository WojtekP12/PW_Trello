package org.test;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.server.VaadinService;
import org.helpers.AddPopup;
import org.models.Card;
import org.models.List;
import org.models.User;
import org.models.Comment;


import static com.vaadin.server.FontAwesome.BOLD;

/**
 * Created by Adam Redliński on 2016-10-20.
 */
public class MySub extends Window {
    private Card card;
    private GridLayout container;
	private GridLayout content;
	
	private RichTextArea commentArea;
	private Button sendComment;
    private Button deleteButton;
	private Button subscribeButton;
	private Button editName;
	private Panel activityPanel;

    public MySub(Card card) {
        super(card.getName()); // Set window caption
		setCard(card);
		init(card);
    }

    public void init(Card card)
    {
        center();

        HorizontalLayout subWindowContainer = new HorizontalLayout();
        content = new GridLayout(11,11);

        content.addComponent(new Label("w liście "),1,1);

        Button button = new Button("lista1");
        button.setStyleName(Reindeer.BUTTON_LINK);
        content .addComponent(button,2,1);
        content.setComponentAlignment(button,Alignment.TOP_LEFT);

        editName = new Button("Edytuj nazwe");
		editName.setStyleName(Reindeer.BUTTON_LINK);
        content.addComponent(editName,1,3);
		addChangeNameButtonListener(editName);

        Label addCommentLabel = new Label("Dodaj komentarz");
        content.addComponent(addCommentLabel,1,4);

        commentArea = new RichTextArea();
        commentArea.setHeight(60f,Unit.MM);
        content.addComponent(commentArea,1,5,9,5);

        sendComment = new Button("Wyślij");
        //sendComment.setDisableOnClick(true);
        content.addComponent(sendComment,1,6);

        Label activityLabel = new Label("Aktywność");
        content.addComponent(activityLabel,0,7);

        Button detailsButton = new Button("Pokaż szczegóły");
        detailsButton.setStyleName(Reindeer.BUTTON_LINK);
        content.addComponent(detailsButton,9,7);

        activityPanel = new Panel();
        activityPanel.setHeight(100f, Unit.MM);
        content.addComponent(activityPanel,1,8,9,8);
		loadComments();
		
		HorizontalLayout labelsLayout = loadLabels();
		content.addComponent(labelsLayout,0,9,9,9);
		
		HorizontalLayout usersLayout = loadMembers();
		content.addComponent(usersLayout, 0,10,9,10);

        content.setMargin(true);

        VerticalLayout rightMenu = new VerticalLayout();
        rightMenu.setSpacing(true);

        Label addLabel = new Label("Dodaj");
        rightMenu.addComponent(addLabel);

        Button membersButton = new Button("Członkowie");
        rightMenu.addComponent(membersButton);
		addMembersButtonClickListener(membersButton);

        Button labelsButton = new Button("Etykiety");
        rightMenu.addComponent(labelsButton);
		addLabelsButtonClickListener(labelsButton);

        Button taskListButton = new Button("Lista zadań");
        rightMenu.addComponent(taskListButton);

        Button termsButton = new Button("Termin");
        rightMenu.addComponent(membersButton);

        Button attatchmentButton = new Button("Załącznik");
        rightMenu.addComponent(attatchmentButton);

        Label activitiesLabel = new Label("Działania");
        rightMenu.addComponent(activitiesLabel);

        Button moveButton = new Button("Przenieś");
        rightMenu.addComponent(moveButton);

        final Button deleteButton = new Button("Usuń");
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                getCard().getList().removeCard(card);
                getUI().getPage().setLocation("/");
            }
        });
        rightMenu.addComponent(deleteButton);

        Button copyButton = new Button("Kopiuj");
        rightMenu.addComponent(copyButton);

        if(card.isSubscribedBy(User.getUserFromSession()))
		{
			subscribeButton = new Button("Zasubskrybowano");
			subscribeButton.addClickListener((Button.ClickListener) clickEvent -> {
				card.removeSubscriber(User.getUserFromSession());
				refreshContent();
			});
		} else
		{
			subscribeButton = new Button("Subskrybuj");
			subscribeButton.addClickListener((Button.ClickListener) clickEvent -> {
				card.addSubscriber(User.getUserFromSession());
				refreshContent();
			});
		}
        rightMenu.addComponent(subscribeButton);

		
        Button archiveButton = new Button("Zarchiwizuj");
		if(card.isArchived())
			archiveButton.setCaption("Przywróć");
        archiveButton.addClickListener((Button.ClickListener) clickEvent -> {
					if(!getCard().isArchived())
					{
						getCard().setArchived(true);
						
						//deleteButton = new Button("Usuń");
						deleteButton.addClickListener(new Button.ClickListener() {
							@Override
							public void buttonClick(Button.ClickEvent event) 
							{
								getCard().getList().removeCard(card);
								Page.getCurrent().reload();
								close();
							}
						});
						
						rightMenu.addComponent(deleteButton);
						archiveButton.setCaption("Przywróć");
					} else
					{
						getCard().setArchived(false);
						
						rightMenu.removeComponent(deleteButton);
						
						archiveButton.setCaption("Zarchiwizuj");
					}
			});
		rightMenu.addComponent(archiveButton);
		

        Button shareButton = new Button("Udostępnij i więcej...");
        shareButton.addStyleName(Reindeer.BUTTON_LINK);
        rightMenu.addComponent(shareButton);

		if(card.isArchived())
		{
			//deleteButton = new Button("Usuń");
			deleteButton.addClickListener((Button.ClickListener) clickEvent -> {
				getCard().getList().removeCard(card);
				Page.getCurrent().reload();
				close();
			});
			rightMenu.addComponent(deleteButton);
		}

        subWindowContainer.addComponents(content, rightMenu);
        setContent(subWindowContainer);
        setClosable(true);
		
		addCommentButtonListener();

    }
    public void setCard(Card card) {this.card = card; }

    public Card getCard() {return this.card; }

    public void setContainer(GridLayout container) {this.container = container; }

    public GridLayout getContainer() {return this.container; }
	
	
	void refreshContent()
	{
		content.removeAllComponents();
		init(card);
	}
	
	void addCommentButtonListener()
	{
		sendComment.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				String commentValue = commentArea.getValue();
				commentArea.clear();

				User user = User.findUser(String.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user")));

				Comment comment = new Comment(user, commentValue);
				comment.setCard(card);

				loadComments();
			}
		});
	}

	void addChangeNameButtonListener(Button button)
	{
		button.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				AddPopup popup = new AddPopup("Change name");
				UI.getCurrent().addWindow(popup);

				popup.getAddButton().addClickListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(Button.ClickEvent event)
					{
						card.setName(popup.getName().getValue());
						popup.close();
						refreshContent();
						Page.getCurrent().reload();
					}
				});

			}
		});
	}
	
	void loadComments()
	{
		VerticalLayout mainCommentLayout = new VerticalLayout();
			mainCommentLayout.setSpacing(true);
		
		int n=card.getCommentsSize();
		for(int i=0;i<n;i++)
		{
			Comment comment = card.getComment(i);
			
			VerticalLayout commentLayout = new VerticalLayout();
				//commentLayout.setMargin(true);
			
				Label username = new Label("<b>" + comment.getAuthor().username + "</b> [ " 
				+ comment.getTime().toString() + " ]",ContentMode.HTML);
				commentLayout.addComponent(username);
				
				Label commentLabel = new Label(comment.getValue(),ContentMode.HTML);
				commentLayout.addComponent(commentLabel);
				
				if(comment.getAuthor() == User.findUser(String.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user"))))
				{
					Button deleteButton = new Button("Delete");
					commentLayout.addComponent(deleteButton);
					addDeleteButtonClickListener(deleteButton, comment);
				}
			
			mainCommentLayout.addComponent(commentLayout);
		}
		
		activityPanel.setContent(mainCommentLayout);
		activityPanel.setScrollTop(Integer.MAX_VALUE);
	}
	
	void addDeleteButtonClickListener(Button button, final Comment comment)
	{
		button.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				comment.remove();
				loadComments();
			}
		});
	}
	
	void addLabelsButtonClickListener(Button button)
	{
		button.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				AddPopup popup = new AddPopup("Add label");
				UI.getCurrent().addWindow(popup);
				
				popup.getAddButton().addClickListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(Button.ClickEvent event)
					{
						card.addLabel(popup.getName().getValue());
						popup.close();
						refreshContent();
					}
				});
			}
		});
	}
	
	HorizontalLayout loadLabels()
	{
		HorizontalLayout l = new HorizontalLayout();
	
		l.addComponent(new Label("Labels: "));
		
		int n = card.getLabelsSize();
		for(int i=0;i<n;i++)
		{
			final String j = card.getLabel(i);
			Button button = new Button(j);
			button.addClickListener(new Button.ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					card.removeLabel(j);
					l.removeComponent(button);
				}
			});
			l.addComponent(button);
		}
		
		l.setSpacing(true);
		
		return l;
	}
	
	void addMembersButtonClickListener(Button button)
	{
		button.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				AddPopup popup = new AddPopup("Add member");
				UI.getCurrent().addWindow(popup);
				
				popup.getAddButton().addClickListener((Button.ClickListener) event1 -> {
                    if(User.userExists(popup.getName().getValue())) // if (USER JEST DODANY DO TABLICY)
                    {
                        User u = User.findUser(popup.getName().getValue());
                        card.addMember(u);
                        card.addSubscriber(u);
                        popup.close();
                        refreshContent();
                    } else
                    {
                        Notification.show("User doesn't exist!");
                    }
                });
			}
		});
	}
	
	HorizontalLayout loadMembers()
	{
		HorizontalLayout l = new HorizontalLayout();
		
		l.addComponent(new Label("Users: "));
		
		int n = card.getMembersSize();
		for(int i=0;i<n;i++)
		{
			final User u = card.getMember(i);
			Button button = new Button(u.username);
			button.addClickListener(new Button.ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					card.removeMember(u);
					l.removeComponent(button);
				}
			});
			l.addComponent(button);
		}
		
		l.setSpacing(true);
		
		return l;
	}

}