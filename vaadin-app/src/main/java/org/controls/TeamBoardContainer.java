package org.controls;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button;
import org.models.Team;
import org.models.User;

public class TeamBoardContainer extends BoardContainer
{
	private Team team;
	
	public TeamBoardContainer(Team team)
	{
		super(team.getName());
		
		if(team.getAdmins().contains(User.getUserFromSession()))
		{
			Button manageButton = new Button("Manage");
			getTitleBar().addComponent(manageButton);
		}
		
		Button leaveButton = new Button("Leave");
		getTitleBar().addComponent(leaveButton);
		
		getTitleBar().setSpacing(true);		
		
		this.team = team;
	}

}