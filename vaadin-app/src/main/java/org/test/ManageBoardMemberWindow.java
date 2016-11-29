package org.test;

import com.vaadin.ui.Window;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import org.models.Team;
import org.models.User;
import org.models.Board;

public class ManageBoardMemberWindow extends Window
{
	private User member;
	private Board board;
	
	public ManageBoardMemberWindow(Board board, User user, ClickEvent event, BoardMenuWindow parent)
	{
		super();
		VerticalLayout content = new VerticalLayout();
		setContent(content);
		setResizable(false);
		setPositionX(event.getClientX());
		setPositionY(event.getClientY());
		
		Button setAdminButton = new Button("Set to admin");
		setAdminButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				board.getAdmins().add(user);
				ManageBoardMemberWindow.this.close();
				parent.refreshContent();
			}
		});
		
		Button removeButton = new Button("Remove");
		removeButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				board.getMembers().remove(user);
				
				if((board.getPrivacy() == Board.BoardPrivacy.PRIVATE) || (board.getPrivacy() == Board.BoardPrivacy.TEAM && board.getTeam().getMembers().contains(user)))
				{
					if(board.getSubscribers().contains(user))
						board.getSubscribers().remove(user);
					
					if(board.getFavourited().contains(user))
					{
						board.getFavourited().remove(user);
						user.getFavouritedBoards().remove(board);
					}
				}
				
				
				ManageBoardMemberWindow.this.close();
				parent.refreshContent();
			}
		});
		
		content.setMargin(true);
		content.setSpacing(true);
		
		content.addComponent(setAdminButton);
		content.addComponent(removeButton);		
	}
	
}