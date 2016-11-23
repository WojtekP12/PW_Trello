package org.models;

import java.util.ArrayList;

public class Team
{
	public static ArrayList<Team> teamsList = new ArrayList<Team>();

	private String name;
	private ArrayList<User> members = new ArrayList<User>();
	private ArrayList<User> admins = new ArrayList<User>();
	private ArrayList<Board> boards = new ArrayList<Board>();
	
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
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Board> getBoards()
	{
		return boards;
	}
}