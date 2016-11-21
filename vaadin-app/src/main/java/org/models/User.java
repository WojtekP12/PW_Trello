package org.models;

import java.util.ArrayList;
import com.vaadin.server.VaadinService;

public class User
{
	public static ArrayList<User> users;
	private String username;
	private String password;
	private String mail;
	private ArrayList<String> notifications = new ArrayList<String>();
	private ArrayList<Board> favouritedBoards = new ArrayList<Board>();
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public User(String username, String password, String mail)
	{
		this.username = username;
		this.password = password;
		this.mail = mail;
	}
	
	public static void testUsers()
	{
		if(users == null)
		{
			users = new ArrayList<User>();
			
			users.add(new User("user1", "12345"));
			users.add(new User("user2", "12345"));
			users.add(new User("user3", "12345"));
			users.add(new User("user4", "12345"));
			users.add(new User("user5", "12345"));
			users.add(new User("user6", "12345"));	
			
			Team.teamsList.add(new Team("Test team 1", findUser("user1")));
			Team.teamsList.add(new Team("Test team 2", findUser("user1")));
		}
	}
	
	public static User findUser(String username)
	{
		int n = User.users.size();
		for(int i=0;i<n;i++)
		{
			if(User.users.get(i).username.equals(username))
				return User.users.get(i);
		}
		
		return null;
	}
	
	public static boolean userExists(String username)
	{
		
		int n = User.users.size();
		for(int i=0;i<n;i++)
		{
			if(users.get(i).username.equals(username))
				return true;
		}
		
		return false;			
	}
	
	public static User getUserFromSession() throws NullPointerException
	{
		return findUser(String.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user")));
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getMail()
	{
		return mail;
	}
	
	public void setMail(String mail)
	{
		this.mail = mail;
	}
	
	public int getNotificationsSize()
	{
		return notifications.size();
	}
	
	public String getNotification(int i)
	{
		return notifications.get(i);
	}
	
	public void addNotification(String notification)
	{
		notifications.add(notification);
	}
	
	public void removeNotification(String notification)
	{
		notifications.remove(notification);
	}

	public ArrayList<Board> getFavouritedBoards()
	{
		return favouritedBoards;
	}
}




