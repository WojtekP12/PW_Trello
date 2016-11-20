package org.models;

import java.util.ArrayList;

public class Team
{
	public static ArrayList<Team> teamsList = new ArrayList<Team>();

	private String name;
	private ArrayList<User> members = new ArrayList<User>();
	private ArrayList<User> admins = new ArrayList<User>();

	public Team(String name, User user)
	{
		this.name = name;
		members.add(user);
		admins.add(user);
	}
	
	public ArrayList<User> getMembers()
	{
		return members;
	}
	
	public ArrayList<User> getAdmins()
	{
		return admins;
	}
	
	
}