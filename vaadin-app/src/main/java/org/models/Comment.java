package org.models;

import java.time.LocalDateTime;

public class Comment
{
	Card parentCard;
	User author;
	String value;
	LocalDateTime time;
		
	public Comment(User author, String value)
	{
		this.author = author;
		this.value = value;
		this.time = LocalDateTime.now();
	}
	
	public Card getCard()
	{
		return parentCard;
	}
	
	public void setCard(Card card)
	{
		this.parentCard = card;
		card.addComment(this);
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	public User getAuthor()
	{
		return this.author;
	}
	
	public LocalDateTime getTime()
	{
		return this.time;
	}
	
	public void remove()
	{
		getCard().removeComment(this);
	}
}
