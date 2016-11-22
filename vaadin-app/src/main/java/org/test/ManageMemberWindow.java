package org.test;

import com.vaadin.ui.Window;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import org.models.Team;
import org.models.User;

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