package org.models;

import java.util.ArrayList;


public class User
{
	public static ArrayList<User> users;
	public String username;
	public String password;
	public String mail;
	
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
		}
	}

}




