package org.test;

import com.vaadin.ui.Window;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import org.models.Team;
import org.models.User;
import org.models.Board;

public class ManageMemberWindow extends Window
{
	private User member;
	private Team team;
	
	public ManageMemberWindow(Team team, User user, ClickEvent event, ManageTeamWindow parent)
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
				team.getAdmins().add(user);
				ManageMemberWindow.this.close();
				parent.refreshContent();
			}
		});
		
		Button removeButton = new Button("Remove");
		removeButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				team.getMembers().remove(user);
				
				int n = team.getBoards().size();
				for(int i=0;i<n;i++)
				{
					Board b = team.getBoards().get(i);
					if(b.getFavourited().contains(user))
					{
						b.getFavourited().remove(user);
						user.getFavouritedBoards().remove(b);
					}
					
					if(b.getSubscribers().contains(user))
						b.getSubscribers().remove(user);
					
					if(b.getAdmins().contains(user))
					{
						if(b.getAdmins().size()==1)
						{
							b.changePrivacy(Board.BoardPrivacy.PUBLIC);
							team.getBoards().remove(b);
						}
						else
						{
							b.getAdmins().remove(user);
							b.getMembers().remove(user);
						}
					} else
						if(b.getMembers().contains(user))
							b.getMembers().remove(user);
				}
				
				ManageMemberWindow.this.close();
				parent.refreshContent();
			}
		});
		
		content.setMargin(true);
		content.setSpacing(true);
		
		content.addComponent(setAdminButton);
		content.addComponent(removeButton);		
	}
}