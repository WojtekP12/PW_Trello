package org.models;

import java.util.ArrayList;

public class Card 
{
	String name;
	String description;
	List parentList;
	boolean archived;
	ArrayList<Comment> comments = new ArrayList<Comment>();
	ArrayList<String> labels = new ArrayList<String>();
	
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
	
	public boolean isArchived()
	{
		return archived;
	}
	
	public void setArchived(boolean b)
	{
		archived = b;
	}
	
	public void addLabel(String label)
	{
		labels.add(label);
	}
	
	public int getLabelsSize()
	{
		return labels.size();
	}
	
	public String getLabel(int i)
	{
		return labels.get(i);
	}
	
	public void removeLabel(String i)
	{
		labels.remove(i);
	}
}
