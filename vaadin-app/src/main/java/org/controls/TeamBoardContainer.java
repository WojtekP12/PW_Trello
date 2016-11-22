package org.controls;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.server.Page;
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
		leaveButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				team.getMembers().remove(User.getUserFromSession());
				if(team.getAdmins().contains(User.getUserFromSession()))
					team.getAdmins().remove(User.getUserFromSession());
				if(team.getAdmins().isEmpty())
					Team.teamsList.remove(team);
				Page.getCurrent().reload();
			}
		});
		
		getTitleBar().setSpacing(true);		
		
		this.team = team;
	}

}