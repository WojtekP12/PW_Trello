package org.controls;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.server.Page;
import org.models.Team;
import org.models.User;
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
				Page.getCurrent().reload();
			}
		});
		
		getTitleBar().setSpacing(true);		
		
		this.team = team;
	}

}