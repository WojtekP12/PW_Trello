package org.controls;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.server.Page;
import org.models.Team;
import org.models.User;
import org.models.Board;
import org.test.ManageTeamWindow;
import com.vaadin.ui.UI;

public class TeamBoardContainer extends BoardContainer
{
	private Team team;
	
	public TeamBoardContainer(Team team)
	{
		super(team.getName());
		
		Button manageButton = new Button("Members");
		getTitleBar().addComponent(manageButton);
		manageButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				ManageTeamWindow mtw = new ManageTeamWindow(event, team);
				UI.getCurrent().addWindow(mtw);	
			}
		});
		
		Button leaveButton = new Button("Leave");
		getTitleBar().addComponent(leaveButton);
		leaveButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				User user = User.getUserFromSession();
				
				team.getMembers().remove(user);				
				if(team.getAdmins().contains(user))
					team.getAdmins().remove(user);
				if(team.getAdmins().isEmpty())
					Team.teamsList.remove(team);
				
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
				
				Page.getCurrent().reload();
			}
		});
		
		getTitleBar().setSpacing(true);		
		
		this.team = team;
	}

}