package org.test;

import java.util.ArrayList;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.*;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import org.models.Board;
import org.models.User;
import org.models.Team;

public class PrivacyWindow extends Window
{
	private Board board;
	private VerticalLayout content;
	
	public PrivacyWindow(ClickEvent clickEvent, Board board)
	{
		super("Privacy");
		this.board = board;
		content = new VerticalLayout();
		
		setPositionX(clickEvent.getClientX());
		setPositionY(clickEvent.getClientY());
		
		setDraggable(false);
		setResizable(false);
		
		setContent(content);
		selectPrivacyView();
	}
	
	private void selectPrivacyView()
	{
		content.removeAllComponents();
		
		Button publicButton = new Button("Public", FontAwesome.UNLOCK);
		content.addComponent(publicButton);
		publicButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				changePrivacyToPublic();
				Page.getCurrent().reload();
			}
		});
			
		Button privateButton = new Button("Private", FontAwesome.LOCK);
		content.addComponent(privateButton);
		privateButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				changePrivacyToPrivate();
				Page.getCurrent().reload();
			}
		});
		
		Button teamButton = new Button("Team", FontAwesome.CUBES);
		content.addComponent(teamButton);	
		teamButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				selectTeamView();
			}
		});		
	}
	
	private void selectTeamView()
	{
		content.removeAllComponents();
		
		Button backButton = new Button("Back");
		content.addComponent(backButton);
		backButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				selectPrivacyView();
			}
		});
		
		User user = User.getUserFromSession();
		int n = Team.teamsList.size();
		for(int i=0;i<n;i++)
		{
			final Team T = Team.teamsList.get(i);
			if(T.getMembers().contains(user))
			{
				Button b = new Button(T.getName());
				content.addComponent(b);
				b.addClickListener(new Button.ClickListener()
				{
					public void buttonClick(ClickEvent event)
					{
						board.setTeam(T);
						changePrivacyToTeam();
						Page.getCurrent().reload();
					}
				});
			}
		}
		
		
		
	}
	
	void changePrivacyToPublic()
	{
		board.changePrivacy(Board.BoardPrivacy.PUBLIC);
	}
	
	void changePrivacyToPrivate()
	{
		board.changePrivacy(Board.BoardPrivacy.PRIVATE);
		
		//Usuń z ulubionych jeżeli użytkownik nie jest członkiem tablicy
		ArrayList<User> toRemoveFromFavourites = new ArrayList<User>();
		
		int n = board.getFavourited().size();
		for(int i=0;i<n;i++)
		{
			if(!board.getMembers().contains(board.getFavourited().get(i)))
				toRemoveFromFavourites.add(board.getFavourited().get(i));
		}
		
		n = toRemoveFromFavourites.size();
		for(int i=0;i<n;i++)
		{
			User u = toRemoveFromFavourites.get(i);
			
			board.getFavourited().remove(u);
			u.getFavouritedBoards().remove(board);
		}
		
		//Usuń z subskrybcji jeżeli użytkownik nie jest członkiem tablicy
		ArrayList<User> toRemoveFromSubscribers = new ArrayList<User>();
		
		n = board.getSubscribers().size();
		for(int i=0;i<n;i++)
		{
			if(!board.getMembers().contains(board.getSubscribers().get(i)))
				toRemoveFromSubscribers.add(board.getSubscribers().get(i));
		}
		
		n = toRemoveFromSubscribers.size();
		for(int i=0;i<n;i++)
		{
			board.getSubscribers().remove(toRemoveFromSubscribers.get(i));
		}
		
		
		
	}
	
	void changePrivacyToTeam()
	{
		board.changePrivacy(Board.BoardPrivacy.TEAM);
		
		//Usuń z członków tablicy jeżeli użytkownik nie jest członkiem teamu
		ArrayList<User> toRemoveFromMembers = new ArrayList<User>();
		
		int n = board.getMembers().size();
		for(int i=0;i<n;i++)
		{
			if(!board.getTeam().getMembers().contains(board.getMembers().get(i)))
				toRemoveFromMembers.add(board.getMembers().get(i));
		}
		
		n = toRemoveFromMembers.size();
		for(int i=0;i<n;i++)
		{
			board.getMembers().remove(toRemoveFromMembers.get(i));
		}
		
		//Usuń z ulubionych jeżeli użytkownik nie jest członkiem teamu
		ArrayList<User> toRemoveFromFavourites = new ArrayList<User>();
		
		n = board.getFavourited().size();
		for(int i=0;i<n;i++)
		{
			if(!board.getTeam().getMembers().contains(board.getFavourited().get(i)))
				toRemoveFromFavourites.add(board.getFavourited().get(i));
		}
		
		n = toRemoveFromFavourites.size();
		for(int i=0;i<n;i++)
		{
			User u = toRemoveFromFavourites.get(i);
			
			board.getFavourited().remove(u);
			u.getFavouritedBoards().remove(board);
		}
		
		//Usuń z subskrybcji jeżeli użytkownik nie jest członkiem teamu
		ArrayList<User> toRemoveFromSubscribers = new ArrayList<User>();
		
		n = board.getSubscribers().size();
		for(int i=0;i<n;i++)
		{
			if(!board.getTeam().getMembers().contains(board.getSubscribers().get(i)))
				toRemoveFromSubscribers.add(board.getSubscribers().get(i));
		}
		
		n = toRemoveFromSubscribers.size();
		for(int i=0;i<n;i++)
		{
			board.getSubscribers().remove(toRemoveFromSubscribers.get(i));
		}
	}

}