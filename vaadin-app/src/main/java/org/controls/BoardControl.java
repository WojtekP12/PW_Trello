package org.controls;

import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.models.Board;

/**
 * Created by wojciech.pelka on 27.10.2016.
 */
public class BoardControl {

    private VerticalLayout board;

    public BoardControl(Board board) {
        Label boardLabel = createLabel(board);
        Button boardButton = createButton(board);
        this.board = new VerticalLayout(boardLabel, boardButton);
    }

    private Button createButton(final Board board) {
        Button boardButton = new Button();
        boardButton.addStyleName("boardPageBoardButtonStyle");
        boardButton.setWidth(300, Sizeable.Unit.PIXELS);
        boardButton.setHeight(100, Sizeable.Unit.PIXELS);
        boardButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("board", board.id);
                boardButton.getUI().getPage().setLocation("/");
            }
        });
        return boardButton;
    }

    private Label createLabel(Board board) {
        Label boardLabel = new Label(board.name);
        boardLabel.addStyleName("boardPageBoardLabelStyle");
        return boardLabel;
    }

    public VerticalLayout getBoard()
    {
        return board;
    }	
}
