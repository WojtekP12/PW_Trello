package org.models;

import java.util.ArrayList;



public class Board
{
	public static ArrayList<Board> boardsList;
	static int cID=0;
	
	public int id;
	public String name;
	public ArrayList<List> lists = new ArrayList<List>();
	
	private ArrayList<User> favourited = new ArrayList<User>();
	private ArrayList<User> members = new ArrayList<User>();
	private ArrayList<User> subscribers = new ArrayList<User>();
	private ArrayList<User> admins = new ArrayList<User>();
	
	private BoardPrivacy privacy;
	
	public enum BoardPrivacy
	{
		PRIVATE, PUBLIC, TEAM;
	}
	
	public Board(String name, User user)
	{
		this.name = name;
		this.id = cID;
		cID++;
		boardsList.add(this);
		this.privacy = BoardPrivacy.PUBLIC;
		this.members.add(user);
		this.admins.add(user);
	}
	
	public void addList(List list)
	{
		lists.add(list);
		list.setBoard(this);
	}
	
	public void removeList(List list)
	{
		lists.remove(list);
	}
	
	
	public static void testBoard()
	{
		if(boardsList == null)
		{
			User.testUsers();
			
			boardsList = new ArrayList<Board>();
			
			Board board = new Board("Test board", User.findUser("user1"));
			
			List list1 = new List("Test list 1");
				list1.addCard(new Card("Test card 1"));
				list1.addCard(new Card("Test card 2"));
				list1.addCard(new Card("Test card 3"));
			List list2 = new List("Test list 2");
				list2.addCard(new Card("Test card 4"));
				list2.addCard(new Card("Test card 5"));
			List list3 = new List("Test list 3");
			
			board.addList(list1);
			board.addList(list2);
			board.addList(list3);
				
			//boardsList.add(board);
			
			Board board2 = new Board("Test board 2", User.findUser("user1"));
			//boardsList.add(board2);
		}
	}
	
	public int size()
	{
		return lists.size();
	}
	
	public List get(int i)
	{
		return lists.get(i);
	}
	
	public ArrayList<User> getFavourited()
	{
		return favourited;
	}
	
	public ArrayList<User> getMembers()
	{
		return members;
	}
	
	public ArrayList<User> getSubscribers()
	{
		return subscribers;
	}
	
	public ArrayList<User> getAdmins()
	{
		return admins;
	}
	
	public void sendNotifications(String notification, User excluded)
	{
		if(!subscribers.isEmpty())
		{
			int n = subscribers.size();
			for(int i=0;i<n;i++)
			{
				if(subscribers.get(i)!=excluded)
					subscribers.get(i).addNotification(notification);
			}
		}
	}
	
	public BoardPrivacy getPrivacy()
	{
		return privacy;
	}
	
	public void changePrivacy(BoardPrivacy newPrivacy)
	{
		this.privacy = newPrivacy;
	}
	
	
}
