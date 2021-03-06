package org.test;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.controls.*;
import org.helpers.AddPopup;
import org.models.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	
	private Board board;
	private String username;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        loadUsernameAndBoard();
		
		final MainContainer layout = new MainContainer();
        setContent(layout.getContainer());

        TitleBar titleBar = new TitleBar(1,30);
        layout.addElement(titleBar.getTitleBar());
        layout.getContainer().setExpandRatio(titleBar.getTitleBar(), 0.2f);

        TitleBarButton boardButton = new TitleBarButton("Boards",FontAwesome.BOOK);
        titleBar.addElement(boardButton.getButton(),0,0,0,1);
		boardButton.getButton().addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				getUI().getPage().setLocation("/BoardsPage");
			}
		});

        TitleBarSearchField searchField = new TitleBarSearchField();
        titleBar.addElement(searchField.getSerachField(),0,0,2,5);

        TitleBarTitleLabel titleLabel = new TitleBarTitleLabel("TRELLO");
        titleBar.addElement(titleLabel.getTitleLabel(),0,0,6,23);

        TitleBarButton addButton = new TitleBarButton("",FontAwesome.PLUS);
        titleBar.addElement(addButton.getButton(),0,0,24,24);

        TitleBarButton userButton = new TitleBarButton(username,FontAwesome.USER);
        titleBar.addElement(userButton.getButton(),0,0,25,27);
		userButton.getButton().addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("user", null);
				getUI().getPage().setLocation("/LoginPage");
			}
		});

        TitleBarButton infoButton = new TitleBarButton("", FontAwesome.INFO);
        titleBar.addElement(infoButton.getButton(),0,0,28,28);

        TitleBarButton notificationButton = new TitleBarButton("",FontAwesome.BELL);
        titleBar.addElement(notificationButton.getButton(),0,0,29,29);
		notificationButton.getButton().addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				NotificationsWindow nw = new NotificationsWindow(event);
				UI.getCurrent().addWindow(nw);
			}
		});


		layout.addElement(nameBar());


        // *********listy*********
        HorizontalLayout tableLayout = new HorizontalLayout();
        tableLayout.setSizeUndefined();
        tableLayout.setSpacing(true);
        tableLayout.setMargin(true);
		
		int n = board.size();

		for(int i=0;i<n;i++)
		{
			final List x = board.get(i);
			if(!x.isArchived())
			{
				Button b = new Button(FontAwesome.PLUS);
				b.setWidth(25,Unit.MM);
				b.addStyleName("loginPageLoginButtonStyle");
				b.addClickListener(new Button.ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						AddPopup popup = new AddPopup("Add Card");
						UI.getCurrent().addWindow(popup);

						popup.getAddButton().addClickListener(new Button.ClickListener() {
							@Override
							public void buttonClick(Button.ClickEvent event) {
								Card c = new Card(popup.getName().getValue());
									c.setList(x);
								x.addCard(c);
								board.logActivity(username+" added card " + c.getName() + ".");
								board.sendNotifications(username+" added card " + c.getName(), User.getUserFromSession());
								popup.close();
								getUI().getPage().reload(); //*****
							}
						});


					}
				});
				Button b1 = new Button(FontAwesome.CLOSE);
				Button b2 = new Button(FontAwesome.BARS);
				b2.setWidth(25,Unit.MM);
				HorizontalLayout but = new HorizontalLayout(b,b2);
				but.setSpacing(true);
				Table t = loadList(board.get(i));
				HorizontalLayout h = new HorizontalLayout(t,b1);
				VerticalLayout v = new VerticalLayout(but,h);
				v.setComponentAlignment(but,Alignment.MIDDLE_CENTER);
				v.setSpacing(true);
				tableLayout.addComponent(v);

				b1.addClickListener(new Button.ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						tableLayout.removeComponent(v);
						x.setArchived(true);
						board.logActivity(username + " archived list" + x.getName() + ".");
						board.sendNotifications(username+" archived list " + x.getName(), User.getUserFromSession());
					}
				});

				addChangeNameButtonListener(b2,x);
			
			  //  tableLayout.setComponentAlignment(t, Alignment.TOP_CENTER);
				tableLayout.setExpandRatio(v, 1.0f);
			}
		}

		addButton.getButton().addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				AddPopup popup = new AddPopup("Add List");
				UI.getCurrent().addWindow(popup);

				popup.getAddButton().addClickListener(new Button.ClickListener() {
					@Override
					public void buttonClick(Button.ClickEvent event) {
						List l = new List(popup.getName().getValue());
							l.setBoard(board);
						board.addList(l);
						board.logActivity(username+" added list " + l.getName() + ".");
						board.sendNotifications(username+" added list " + l.getName(), User.getUserFromSession());
						popup.close();

						Button b = new Button(FontAwesome.PLUS);
						b.addStyleName("loginPageLoginButtonStyle");
						b.setWidth(25,Unit.MM);
						b.addClickListener(new Button.ClickListener() {
							@Override
							public void buttonClick(ClickEvent event) {
								AddPopup popup = new AddPopup("Add Card");
								UI.getCurrent().addWindow(popup);

								popup.getAddButton().addClickListener(new Button.ClickListener() {
									@Override
									public void buttonClick(Button.ClickEvent event) {
										Card c = new Card(popup.getName().getValue());
											c.setList(l);
										l.addCard(c);
										board.logActivity(username+" added card " + c.getName() + ".");
										board.sendNotifications(username+" added card " + c.getName(), User.getUserFromSession());
										popup.close();
										getUI().getPage().reload(); //*****
									}
								});


							}
						});
						Button b1 = new Button(FontAwesome.CLOSE);
						Button b2 = new Button(FontAwesome.BARS);
						b2.setWidth(25,Unit.MM);
						HorizontalLayout but = new HorizontalLayout(b,b2);
						but.setSpacing(true);
						Table t = loadList(l);
						HorizontalLayout h = new HorizontalLayout(t,b1);
						VerticalLayout v = new VerticalLayout(but,h);
						v.setComponentAlignment(but,Alignment.MIDDLE_CENTER);
						v.setSpacing(true);
						tableLayout.addComponent(v);

						b1.addClickListener(new Button.ClickListener() {
							@Override
							public void buttonClick(ClickEvent event) {
								tableLayout.removeComponent(v);
							}
						});

						addChangeNameButtonListener(b2,l);

					}
				});

			}
		});

      /*  Panel panel = new Panel("Card");
        panel.addStyleName(Reindeer.PANEL_LIGHT);
        panel.setSizeFull();
        panel.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent clickEvent) {
                MySub sub = new MySub();

                // Add it to the root component
                UI.getCurrent().addWindow(sub);
            }
        });*/

		layout.addElement(tableLayout);
        layout.getContainer().setExpandRatio(tableLayout,0.8f);

        setContent(layout.getContainer());
    }

    @WebServlet(urlPatterns = "BoardView/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
	
	Table loadList(List list)
	{
		Table table = new Table();
			table.setStyleName(Reindeer.LAYOUT_BLACK);
			table.setWidth(60,Unit.MM);
			table.setSelectable(true);
			table.addContainerProperty(list.getName(), Panel.class, null);

		int n = list.size();

		for(int i=0;i<n;i++)
		{
			if(!list.get(i).isArchived())
			{
				Panel panel = loadCard(list.get(i),i);
				table.addItem(new Object[] {panel},  i);
			}
		}
		
		table.setPageLength(table.size());
		
		return table;
	}
	
	Panel loadCard(Card card, int cardIndex)
	{
		Panel panel = new Panel(card.getName());
			panel.addStyleName(Reindeer.PANEL_LIGHT);
			panel.setSizeFull();
		
		addCardClickListener(panel,card);

		return panel;
	}

	void addCardClickListener(Panel panel, final Card card)
	{
		panel.addClickListener(new MouseEvents.ClickListener()
		{
			@Override
			public void click(MouseEvents.ClickEvent clickEvent)
			{
				Notification.show(card.getName());
				MySub sub = new MySub(card);
				UI.getCurrent().addWindow(sub);
			}
		});
	}

	void addChangeNameButtonListener(Button button, List list)
	{
		button.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				AddPopup popup = new AddPopup("Change list name");
				UI.getCurrent().addWindow(popup);

				popup.getAddButton().addClickListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(Button.ClickEvent event)
					{
						list.setName(popup.getName().getValue());
						popup.close();
						Page.getCurrent().reload();
					}
				});

			}
		});
	}

	void loadUsernameAndBoard()
	{
		username = String.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user"));
		if(username == "null")
			getUI().getPage().setLocation("/HomePage");
		
		Board.testBoard();
		User.testUsers();
		
		try
		{
			board = Board.boardsList.get(0);
			int id = Integer.valueOf(String.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("board")));
			
			int n = Board.boardsList.size();
			for(int i=0;i<n;i++)
			{
				if(Board.boardsList.get(i).id==id)
				{
					board = Board.boardsList.get(i);
					break;
				}
			}
		}
		 catch (Exception e)
		{
			getUI().getPage().setLocation("/BoardsPage");
		}
		
		
	}

	HorizontalLayout nameBar()
	{
		HorizontalLayout layout = new HorizontalLayout();
			layout.setMargin(true);
			layout.setSpacing(true);
			
		Button nameButton = new Button(board.name);
		layout.addComponent(nameButton);
		if(board.getAdmins().contains(User.getUserFromSession()))
		{
			nameButton.addClickListener(new Button.ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					AddPopup popup = new AddPopup("Change board name");
					UI.getCurrent().addWindow(popup);

					popup.getAddButton().addClickListener(new Button.ClickListener()
					{
						public void buttonClick(ClickEvent event)
						{
							board.name = popup.getName().getValue();
							popup.close();
							Page.getCurrent().reload();
						}
					});
				}
			});
		}
		
		if(board.getPrivacy()==Board.BoardPrivacy.TEAM)
		{
			Button teamButton = new Button(board.getTeam().getName());
			layout.addComponent(teamButton);
		}
		
		Button favouriteButton;
		if(User.getUserFromSession().getFavouritedBoards().contains(board))
		{
			favouriteButton = new Button("",FontAwesome.STAR);
			favouriteButton.addClickListener(new Button.ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					User user = User.getUserFromSession();
					
					board.getFavourited().remove(user);
					user.getFavouritedBoards().remove(board);
					Page.getCurrent().reload();
				}
			});
		}
		else
		{
			favouriteButton = new Button("",FontAwesome.STAR_O);
			favouriteButton.addClickListener(new Button.ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					User user = User.getUserFromSession();
					
					board.getFavourited().add(user);
					user.getFavouritedBoards().add(board);
					Page.getCurrent().reload();
				}
			});
		}
		layout.addComponent(favouriteButton);
		
		Button privacyButton;
		switch(board.getPrivacy())
		{
			case PUBLIC:
				privacyButton = new Button("Public",FontAwesome.UNLOCK);
				break;
			case PRIVATE:
				privacyButton = new Button("Private",FontAwesome.LOCK);
				break;
			case TEAM:
				privacyButton = new Button("Team",FontAwesome.CUBES);
				break;
			default:
				privacyButton = new Button();
				break;
		}
		layout.addComponent(privacyButton);
		if(board.getAdmins().contains(User.getUserFromSession()))
			privacyButton.addClickListener(new Button.ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					PrivacyWindow pw = new PrivacyWindow(event, board);
					UI.getCurrent().addWindow(pw);
				}
			});
			
		Button menuButton = new Button("Menu");
		menuButton.addClickListener(new Button.ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					BoardMenuWindow bmw = new BoardMenuWindow(board);
					UI.getCurrent().addWindow(bmw);
				}
			});
		layout.addComponent(menuButton);
		
		return layout;
	}
}
