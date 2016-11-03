package org.models;

import java.util.ArrayList;


public class Board
{
	public static ArrayList<Board> boardsList;
	static int cID=0;
	
	public int id;
	public String name;
	public ArrayList<List> lists = new ArrayList<List>();
	
	public Board(String name)
	{
		this.name = name;
		this.id = cID;
		cID++;
		boardsList.add(this);
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
			boardsList = new ArrayList<Board>();
			
			Board board = new Board("Test board");
			
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
			
			Board board2 = new Board("Test board 2");
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
}
