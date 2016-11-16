package org.controls;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.server.VaadinService;
import org.models.*;

/**
 * Created by wojciech.pelka on 27.10.2016.
 */
public class BoardControl
{
    VerticalLayout board;
    Button deleteButton;

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

        deleteButton = new Button(FontAwesome.CLOSE);

        HorizontalLayout h = new HorizontalLayout(boardButton,deleteButton);

        board = new VerticalLayout(boardLabel, h);
    }

    public Button GetDeleteButton()
    {
        return deleteButton;
    }


    public VerticalLayout getBoard()
    {
        return board;
    }	
}
