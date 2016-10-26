package org.test;

/**
 * Created by wojciech.pelka on 26.10.2016.
 */

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.controls.*;

import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
public class BoardsPage extends UI
{
    @Override
    protected void init(VaadinRequest request)
    {
        final MainContainer layout = new MainContainer("boardsPageMainContainerStyle");
        setContent(layout.getContainer());

        TitleBar titleBar = new TitleBar(1,30);
        layout.addElement(titleBar.getTitleBar());
        layout.getContainer().setExpandRatio(titleBar.getTitleBar(), 0.2f);

        TitleBarButton boardButton = new TitleBarButton("Boards", FontAwesome.BOOK);
        titleBar.addElement(boardButton.getButton(),0,0,0,1);

        TitleBarSearchField searchField = new TitleBarSearchField();
        titleBar.addElement(searchField.getSerachField(),0,0,2,5);

        TitleBarTitleLabel titleLabel = new TitleBarTitleLabel("TRELLO");
        titleBar.addElement(titleLabel.getTitleLabel(),0,0,6,23);

        TitleBarButton addButton = new TitleBarButton("",FontAwesome.PLUS);
        titleBar.addElement(addButton.getButton(),0,0,24,24);

        TitleBarButton userButton = new TitleBarButton("User",FontAwesome.USER);
        titleBar.addElement(userButton.getButton(),0,0,25,27);

        TitleBarButton infoButton = new TitleBarButton("", FontAwesome.INFO);
        titleBar.addElement(infoButton.getButton(),0,0,28,28);

        TitleBarButton notificationButton = new TitleBarButton("",FontAwesome.BELL);
        titleBar.addElement(notificationButton.getButton(),0,0,29,29);


        Label label = new Label("Personal label");
        label.setIcon(FontAwesome.BOOK);

        Button board = new Button("Board");
        board.addStyleName("boardPageBoardButtonStyle");
        board.setWidth(300,Unit.PIXELS);
        board.setHeight(150,Unit.PIXELS);

        HorizontalLayout boards = new HorizontalLayout(board);
        boards.setSizeUndefined();
        VerticalLayout childLayout = new VerticalLayout(boards);
        childLayout.setComponentAlignment(boards,Alignment.MIDDLE_CENTER);
        layout.addElement(childLayout);
        layout.getContainer().setComponentAlignment(childLayout, Alignment.MIDDLE_CENTER);

    }

    @WebServlet(urlPatterns = "BoardsPage/*", name = "BoardsPageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = BoardsPage.class, productionMode = false)
    public static class BoardsPageServlet extends VaadinServlet {
    }
}
