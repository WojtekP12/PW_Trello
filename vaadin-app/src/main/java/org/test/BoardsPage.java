package org.test;

/**
 * Created by wojciech.pelka on 26.10.2016.
 */

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.Page;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import org.controls.*;
import org.helpers.AddPopup;
import org.models.*;
import org.models.User;
import org.controls.BoardContainer;


import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
public class BoardsPage extends UI
{
	private String username;
	
	
    @Override
    protected void init(VaadinRequest request)
    {
        loadUsername();
		
		final MainContainer layout = new MainContainer("boardsPageMainContainerStyle");
        setContent(layout.getContainer());

        TitleBar titleBar = new TitleBar(1,30);
        layout.addElement(titleBar.getTitleBar());
        layout.getContainer().setExpandRatio(titleBar.getTitleBar(), 0.1f);

        TitleBarButton boardButton = new TitleBarButton("Boards", FontAwesome.BOOK);
        titleBar.addElement(boardButton.getButton(),0,0,0,1);

        TitleBarSearchField searchField = new TitleBarSearchField();
        titleBar.addElement(searchField.getSerachField(),0,0,2,5);

        TitleBarTitleLabel titleLabel = new TitleBarTitleLabel("TRELLO");
        titleBar.addElement(titleLabel.getTitleLabel(),0,0,6,23);

        TitleBarButton addButton = new TitleBarButton("",FontAwesome.PLUS);
		addButton.getButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                AddPopup popup = new AddPopup("Add Board");
                UI.getCurrent().addWindow(popup);
                popup.getAddButton().addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Board b = new Board(popup.getName().getValue(), User.getUserFromSession());
                        /*BoardControl board = new BoardControl(b);
                        board.GetDeleteButton().addClickListener(new Button.ClickListener() {
                            @Override
                            public void buttonClick(ClickEvent event) {

                                boardList.removeComponent(board.getBoard());
                                h.removeComponent(board.getBoard());
                                Board.boardsList.remove(b);
                            }
                        });

                        h.addComponent(board.getBoard());
                        boardList.addComponent(h);*/
                        popup.close();
						Page.getCurrent().reload();
                    }
                });

            }
        });

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
		
		Board.testBoard();
				
		VerticalLayout boardContainersList = new VerticalLayout();
		
		User user = User.getUserFromSession();
		
		if(!user.getFavouritedBoards().isEmpty())
		{
			BoardContainer favouritesContainer = new BoardContainer("Favourites");
			
			int n = user.getFavouritedBoards().size();
			for(int i=0;i<n;i++)
			{
				final Board B = user.getFavouritedBoards().get(i);
				favouritesContainer.addBoardToContainer(B);
			}
			
			boardContainersList.addComponent(favouritesContainer);
		}
		
		BoardContainer publicContainer = new BoardContainer("Public");
		BoardContainer privateContainer = new BoardContainer("Private");
		
		int n = Board.boardsList.size();
		for(int i=0;i<n;i++)
		{
			final Board B = Board.boardsList.get(i);
			switch(B.getPrivacy())
			{
				case PUBLIC:
					publicContainer.addBoardToContainer(B);
					break;
				case PRIVATE:
					if(B.getMembers().contains(user))
						privateContainer.addBoardToContainer(B);
					break;
				case TEAM:
					break;
				default:
					break;
			}
		}
		
		boardContainersList.addComponent(publicContainer);
		boardContainersList.addComponent(privateContainer);
		
		n = Team.teamsList.size();
		for(int i=0;i<n;i++)
		{
			final Team T = Team.teamsList.get(i);
			if(T.getMembers().contains(user))
			{
				TeamBoardContainer teamBoardContainer = new TeamBoardContainer(T);
				int m = T.getBoards().size();
				for(int j=0;j<m;j++)
				{
					teamBoardContainer.addBoardToContainer(T.getBoards().get(j));
				}
				boardContainersList.addComponent(teamBoardContainer);
			}
		}


		boardContainersList.setSpacing(true);
		boardContainersList.setMargin(true);
		layout.addElement(boardContainersList);
		layout.getContainer().setExpandRatio(boardContainersList,0.8f);
    }

    @WebServlet(urlPatterns = "BoardsPage/*", name = "BoardsPageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = BoardsPage.class, productionMode = false)
    public static class BoardsPageServlet extends VaadinServlet {
    }
	
	void loadUsername()
	{
		username = String.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user"));
		/*if(username == "null")
			getUI().getPage().setLocation("/HomePage");*/
		
		
	}
	
	
	
}
