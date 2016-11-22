package org.test;

import org.models.Team;
import org.models.User;
import org.helpers.AddPopup;
import com.vaadin.ui.Window;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class ManageTeamWindow extends Window
{
	private Team team;
	VerticalLayout content;
	
	public ManageTeamWindow(ClickEvent clickEvent, Team team)
	{
		super("Members");
		this.team = team;
		content = new VerticalLayout();
		
		content.setMargin(true);
		content.setSpacing(true);
		
		setPositionX(clickEvent.getClientX());
		setPositionY(clickEvent.getClientY());
				
		setContent(content);
		init();		
	}
	
	public void init()
	{
		GridLayout adminsGrid = new GridLayout();
			adminsGrid.setColumns(5);
			adminsGrid.setSpacing(true);
		
		adminsGrid.addComponent(new Label("Admins: "));
		int n = team.getAdmins().size();
		for(int i=0;i<n;i++)
		{
			final User U = team.getAdmins().get(i);
			Button button = new Button(U.getUsername());
			adminsGrid.addComponent(button);
		}
		
		GridLayout membersGrid = new GridLayout();
			membersGrid.setColumns(5);
			membersGrid.setSpacing(true);
			
		membersGrid.addComponent(new Label("Members: "));
		n = team.getMembers().size();
		for(int i=0;i<n;i++)
		{
			final User U = team.getMembers().get(i);
			if(!team.getAdmins().contains(U))
			{
				Button button = new Button(U.getUsername());
				membersGrid.addComponent(button);
				if(team.getAdmins().contains(User.getUserFromSession()))
				{
					button.addClickListener(new Button.ClickListener()
					{
						public void buttonClick(ClickEvent event)
						{
							ManageMemberWindow mmw = new ManageMemberWindow(team, U, event, ManageTeamWindow.this);
							UI.getCurrent().addWindow(mmw);
						}
					});
				}
			}
		}
		
		content.addComponent(adminsGrid);
		content.addComponent(membersGrid);
		
		
		if(team.getAdmins().contains(User.getUserFromSession()))
		{
			Button addMemberButton = new Button("Add Member");
			content.addComponent(addMemberButton);
			addMemberButton.addClickListener(new Button.ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					AddPopup popup = new AddPopup("Add team member");
					UI.getCurrent().addWindow(popup);
					
					popup.getAddButton().addClickListener(new Button.ClickListener()
					{
						public void buttonClick(ClickEvent event)
						{
							if(User.userExists(popup.getName().getValue()))
							{
								User u = User.findUser(popup.getName().getValue());
								if(!team.getMembers().contains(u))
								{
									team.getMembers().add(u);
									popup.close();
									refreshContent();
								} else
									Notification.show("User is already in team!");									
							} else
								Notification.show("User doesn't exist!");
						}
					});
				}
			});
		}
	}
	
	void refreshContent()
	{
		content.removeAllComponents();
		init();
	}

}