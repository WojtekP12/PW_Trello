package org.models;

import com.vaadin.server.FontAwesome;

import java.util.ArrayList;

public class List 
{
	String name;
	Board parentBoard;
	boolean archived = false;
	public ArrayList<Card> cards = new ArrayList<Card>();
	
	public List(String name)
	{
		this.name = name;
	}
	
	public void addCard(Card card)
	{
		cards.add(card);
		card.setList(this);
	}
	
	public void removeCard(Card card)
	{
		cards.remove(card);
	}
	
	public void moveCard(Card card, List target)
	{
		target.addCard(card);
		card.setList(target);
		removeCard(card);
	}
	
	public int size()
	{
		return cards.size();
	}
	
	public Card get(int i)
	{
		return cards.get(i);
	}
	
	public String getName()
	{
		return name;
	}
	
	public Board getBoard()
	{
		return parentBoard;
	}
	
	public void setBoard(Board board)
	{
		this.parentBoard = board;
	}
	
	public boolean isArchived()
	{
		return archived;
	}
	
	public void setArchived(boolean b)
	{
		archived = b;
	}

	public void setName(String name) {
		this.name = name;
	}
}
