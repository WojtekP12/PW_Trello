package org.controls;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import org.models.*;

/**
 * Created by wojciech.pelka on 27.10.2016.
 */
public class BoardControl
{
    VerticalLayout board;

    public BoardControl(String boardName)
    {
        Label boardLabel = new Label(boardName);
        boardLabel.addStyleName("boardPageBoardLabelStyle");
        Button boardButton = new Button();
        boardButton.addStyleName("boardPageBoardButtonStyle");
        boardButton.setWidth(300, Sizeable.Unit.PIXELS);
        boardButton.setHeight(100, Sizeable.Unit.PIXELS);

        board = new VerticalLayout(boardLabel, boardButton);
    }

	
    public BoardControl(Board b)
    {
        Label boardLabel = new Label(b.name);
        boardLabel.addStyleName("boardPageBoardLabelStyle");
        Button boardButton = new Button();
        boardButton.addStyleName("boardPageBoardButtonStyle");
        boardButton.setWidth(300, Sizeable.Unit.PIXELS);
        boardButton.setHeight(100, Sizeable.Unit.PIXELS);
		boardButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("board", b.id);
				boardButton.getUI().getPage().setLocation("/");
			}
		});

        board = new VerticalLayout(boardLabel, boardButton);
    }
	
    public VerticalLayout getBoard()
    {
        return board;
    }	
}
