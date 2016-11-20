package org.test;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import org.models.User;



public class NotificationsWindow extends Window 
{
	private User user;
	private Panel content;
	
	
	public NotificationsWindow(ClickEvent clickEvent)
	{
		super("Notifications");
		user = User.getUserFromSession();
		content = new Panel();
		
		setPositionX(clickEvent.getClientX()-300);
		setPositionY(clickEvent.getClientY());
		
		setHeight("300px");
		setWidth("330px");
		
		setDraggable(false);
		setResizable(false);
		
		setContent(content);
		init();
	}
	
	private void init()
	{
		VerticalLayout layout = new VerticalLayout();
			layout.setMargin(true);
			layout.setSpacing(true);			
		
		try
		{
			int n = user.getNotificationsSize();
			for(int i=0;i<n;i++)
			{
				final String J = user.getNotification(i);
				Button b = new Button(J);
				layout.addComponent(b);
				b.addClickListener(new Button.ClickListener()
				{
					public void buttonClick(ClickEvent event)
					{
						user.removeNotification(J);
						layout.removeComponent(b);
					}
				});
				
			}
		} catch(Exception e)
		{
			Label l = new Label("No notifications!");
			layout.addComponent(l);
		}
		
		content.setContent(layout);
	}



}