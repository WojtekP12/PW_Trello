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
        layout.getContainer().setExpandRatio(titleBar.getTitleBar(), 0.1f);

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

        BoardControl board1 = new BoardControl("Board1");
        BoardControl board2 = new BoardControl("Board2");
        BoardControl board3 = new BoardControl("Board3");

        HorizontalLayout boardList = new HorizontalLayout(board1.getBoard(), board2.getBoard(), board3.getBoard());
        boardList.setSpacing(true);
        VerticalLayout childLayout = new VerticalLayout(boardList);
        childLayout.setComponentAlignment(boardList,Alignment.MIDDLE_CENTER);

        layout.addElement(childLayout);
        layout.getContainer().setExpandRatio(childLayout,0.8f);

    }

    @WebServlet(urlPatterns = "BoardsPage/*", name = "BoardsPageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = BoardsPage.class, productionMode = false)
    public static class BoardsPageServlet extends VaadinServlet {
    }
}