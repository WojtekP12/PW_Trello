package org.test;

import com.vaadin.ui.Window;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import org.models.List;
import org.models.Board;
import org.models.Card;
import org.models.User;

public class ArchivedItemsWindow extends Window
{
	private Board board;
	private VerticalLayout content;
	
	public ArchivedItemsWindow(Board board)
	{
		super("Archived Items");
		this.board = board;
		this.content = new VerticalLayout();
		
		content.setMargin(true);
		content.setSpacing(true);
		
		setContent(content);
		initLists();
	}
	
	void initLists()
	{
		content.removeAllComponents();
		
		Button cardsButton = new Button("Cards");
		cardsButton.addClickListener((Button.ClickListener) clickEvent ->
		{
			initCards();
		});
		content.addComponent(cardsButton);
		
		
		VerticalLayout vl = new VerticalLayout();
		
		vl.setSpacing(true);
		vl.setMargin(true);
		
		int n = board.lists.size();
		for(int i=0;i<n;i++)
		{
			if(board.lists.get(i).isArchived())
			{
				final List L = board.lists.get(i);
				HorizontalLayout hl = new HorizontalLayout();
				hl.addComponent(new Label(L.getName()));
				
				Button sendButton = new Button("Send to board");
				sendButton.addClickListener((Button.ClickListener) clickEvent ->
				{
					L.setArchived(false);
					initLists();
					board.logActivity(User.getUserFromSession().getUsername()+" sent list " + L.getName() + " back to board.");
				});
				hl.addComponent(sendButton);
				
				Button removeButton = new Button("Remove");
				removeButton.addClickListener((Button.ClickListener) clickEvent ->
				{
					board.lists.remove(L);
					initLists();
					board.logActivity(User.getUserFromSession().getUsername()+" removed list " + L.getName() + ".");
				});
				hl.addComponent(removeButton);
				
				hl.setSpacing(true);
				hl.setMargin(true);
				
				vl.addComponent(hl);
			}
		}
		
		content.addComponent(vl);
	}

	void initCards()
	{
		content.removeAllComponents();
		
		Button listsButton = new Button("Lists");
		listsButton.addClickListener((Button.ClickListener) clickEvent ->
		{
			initLists();
		});
		content.addComponent(listsButton);
		
		
		VerticalLayout vl = new VerticalLayout();
		
		vl.setSpacing(true);
		vl.setMargin(true);
		
		int n = board.lists.size();
		for(int i=0;i<n;i++)
		{
			int m = board.lists.get(i).cards.size();
			for(int j=0;j<m;j++)
			{
				final List L = board.lists.get(i);
				if(L.cards.get(j).isArchived())
				{
					final Card C = L.cards.get(j);
					HorizontalLayout hl = new HorizontalLayout();
					hl.addComponent(new Label(C.getName()));
					
					Button sendButton = new Button("Send to board");
					sendButton.addClickListener((Button.ClickListener) clickEvent ->
					{
						C.setArchived(false);
						board.logActivity(User.getUserFromSession().getUsername()+" sent card " + C.getName() + " back to board.");
						initCards();
					});
					hl.addComponent(sendButton);
					
					Button removeButton = new Button("Remove");
					removeButton.addClickListener((Button.ClickListener) clickEvent ->
					{
						L.cards.remove(C);
						board.logActivity(User.getUserFromSession().getUsername()+" removed card " + C.getName() + ".");
						initCards();
					});
					hl.addComponent(removeButton);
					
					hl.setSpacing(true);
					hl.setMargin(true);
					
					vl.addComponent(hl);
					
				}
			
			}
		}
		
		content.addComponent(vl);
	}

}