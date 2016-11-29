package org.test;

import com.vaadin.ui.Window;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Label;
import org.models.User;
import org.models.Board;
import org.helpers.AddPopup;


public class BoardMenuWindow extends Window
{
	private Board board;
	private VerticalLayout content;
	
	public BoardMenuWindow(Board board)
	{
		super("Board Menu");
		this.board = board;
		this.content = new VerticalLayout();
		
		content.setMargin(true);
		content.setSpacing(true);
		
		setContent(content);
		init();
	}
	
	void init()
	{
		content.addComponent(loadMembers());
	}
	
	void refreshContent()
	{
		content.removeAllComponents();
		init();
	}
	
	VerticalLayout loadMembers()
	{
		VerticalLayout vl = new VerticalLayout();
		
		GridLayout adminsGrid = new GridLayout();
			adminsGrid.setColumns(5);
			adminsGrid.setSpacing(true);
			
		adminsGrid.addComponent(new Label("Admins: "));
		int n = board.getAdmins().size();
		for(int i=0;i<n;i++)
		{
			final User U = board.getAdmins().get(i);
			Button button = new Button(U.getUsername());
			adminsGrid.addComponent(button);
		}
		
		GridLayout membersGrid = new GridLayout();
			membersGrid.setColumns(5);
			membersGrid.setSpacing(true);
			
		membersGrid.addComponent(new Label("Members: "));
		n = board.getMembers().size();
		for(int i=0;i<n;i++)
		{
			final User U = board.getMembers().get(i);
			if(!board.getAdmins().contains(U))
			{
				Button button = new Button(U.getUsername());
				membersGrid.addComponent(button);
				if(board.getAdmins().contains(User.getUserFromSession()))
				{
					button.addClickListener(new Button.ClickListener()
					{
						public void buttonClick(ClickEvent event)
						{
							ManageBoardMemberWindow mbmw = new ManageBoardMemberWindow(board, U, event, BoardMenuWindow.this);
							UI.getCurrent().addWindow(mbmw);
						}
					});
				}
			}
		}
		
		vl.addComponent(adminsGrid);
		vl.addComponent(membersGrid);
		
		if(board.getAdmins().contains(User.getUserFromSession()))
		{
			Button addMemberButton = new Button("Add Member");
			vl.addComponent(addMemberButton);
			addMemberButton.addClickListener(new Button.ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					AddPopup popup = new AddPopup("Add board member");
					UI.getCurrent().addWindow(popup);
					
					popup.getAddButton().addClickListener(new Button.ClickListener()
					{
						public void buttonClick(ClickEvent event)
						{
							if(User.userExists(popup.getName().getValue()))
							{
								User u = User.findUser(popup.getName().getValue());
								if(!board.getMembers().contains(u))
								{
									board.getMembers().add(u);
									popup.close();
									refreshContent();
								} else
									Notification.show("User is already member of this board!");									
							} else
								Notification.show("User doesn't exist!");
						}
					});
				}
			});
		}
		
		return vl;
	}
	
	
	
}