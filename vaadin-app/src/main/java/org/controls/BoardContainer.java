package org.controls;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import org.models.Board;

public class BoardContainer extends VerticalLayout
{
	private HorizontalLayout boardsContainer;
	private HorizontalLayout titleBar;
	
	public BoardContainer(String containerName)
    {
		super();
		
		titleBar = new HorizontalLayout();
			Button nameButton = new Button(containerName);
			titleBar.addComponent(nameButton);
		
		Panel panel = new Panel();
			boardsContainer = new HorizontalLayout();
				boardsContainer.setMargin(true);
				boardsContainer.setSpacing(true);
			panel.setContent(boardsContainer);
		
		this.addComponent(titleBar);
		this.addComponent(panel);
    }
	
	public void addBoardToContainer(final Board board)
	{
		BoardControl boardControl = new BoardControl(board);
		boardControl.GetDeleteButton().addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    boardsContainer.removeComponent(boardControl.getBoard());
					if(board.getPrivacy() == Board.BoardPrivacy.TEAM)
						board.getTeam().getBoards().remove(board);
					int n = board.getFavourited().size();
					for(int i=0;i<n;i++)
					{
						board.getFavourited().get(i).getFavouritedBoards().remove(board);
					}
					
                    Board.boardsList.remove(board);
					
                }
            });
		boardsContainer.addComponent(boardControl.getBoard());
	}
	
	public HorizontalLayout getTitleBar()
	{
		return titleBar;
	}
	
	
}