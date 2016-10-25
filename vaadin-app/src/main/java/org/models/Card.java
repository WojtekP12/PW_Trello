package org.models;

public class Card 
{
	String name;
	String description;
	List parentList;
	
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
}
