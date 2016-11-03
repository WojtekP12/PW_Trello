package org.models;

import java.util.ArrayList;

public class Card 
{
	String name;
	String description;
	List parentList;
	ArrayList<Comment> comments = new ArrayList<Comment>();
	
	public Card(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public List getList()
	{
		return parentList;
	}
	
	public void setList(List list)
	{
		this.parentList = list;
	}

	public void setDescription(String description)	{this.description = description; }

	public String getDescription() { return this.description; }
	
	public void addComment(Comment c)
	{
		comments.add(c);
	}
	
	public int getCommentsSize()
	{
		return comments.size();
	}
	
	public Comment getComment(int i)
	{
		return comments.get(i);
	}
	
	public void removeComment(Comment c)
	{
		comments.remove(c);
	}

	public void removeCard(Card card) {getList().removeCard(this);}
}
