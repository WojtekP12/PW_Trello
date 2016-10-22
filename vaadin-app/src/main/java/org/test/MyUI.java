package org.test;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import javafx.scene.text.Font;
import org.controls.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final MainContainer layout = new MainContainer();
        setContent(layout.getContainer());

        TitleBar titleBar = new TitleBar(1,30);
        layout.addElement(titleBar.getTitleBar());

        TitleBarButton boardButton = new TitleBarButton("Boards",FontAwesome.BOOK);
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


        // *********listy*********
        HorizontalLayout tableLayout = new HorizontalLayout();
        tableLayout.setSizeFull();
        tableLayout.setSpacing(true);
        tableLayout.setMargin(true);
        

        Table table = new Table("Table");
        table.setStyleName(Reindeer.LAYOUT_BLACK);
        table.setWidth(50,Unit.MM);
        table.setSelectable(true);

        Table table2 = new Table("Table2");
        table.setStyleName(Reindeer.LAYOUT_BLACK);
        table.setWidth(50,Unit.MM);
        table.setSelectable(true);

        tableLayout.addComponent(table);
        tableLayout.addComponent(table2);

// Define two columns for the built-in container
        table.addContainerProperty("Name", Panel.class, null);
        table2.addContainerProperty("Name2",Panel.class, null);

        Panel panel = new Panel("Card");
        panel.addStyleName(Reindeer.PANEL_LIGHT);
        panel.setSizeFull();
        panel.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent clickEvent) {
                MySub sub = new MySub();

                // Add it to the root component
                UI.getCurrent().addWindow(sub);
            }
        });

       Panel panel2 = new Panel("Card2");
        panel2.addStyleName(Reindeer.PANEL_LIGHT);
        panel2.setSizeFull();
        panel2.addClickListener((MouseEvents.ClickListener) clickEvent -> {
            MySub sub = new MySub();

            // Add it to the root component
            UI.getCurrent().addWindow(sub);
        });

         Panel panel3 = new Panel("Card3");
        panel3.addStyleName(Reindeer.PANEL_LIGHT);
        panel3.setSizeFull();
        panel3.setContent(new Label("pt, 3 paź 2016"));
        panel3.addClickListener((MouseEvents.ClickListener) clickEvent -> {
            MySub sub = new MySub();

            // Add it to the root component
            UI.getCurrent().addWindow(sub);
        });

        table.addItem(new Object[] {panel},  1);
        table.addItem(new Object[] {panel2},  2);
        table.setPageLength(table.size());

        table2.addItem(new Object[] {panel3}, 3);
        table2.setPageLength(table.size());

        layout.addElement(tableLayout);
//        layout.setMargin(true);
//        layout.setSpacing(true);

//        layout.addStyleName("mainContainerStyle");
//        layout.setHeight("100%");
        setContent(layout.getContainer());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
