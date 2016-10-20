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
        final VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("mainContainerStyle");
        layout.setHeight("100%");
        setContent(layout);

        GridLayout titleBar = new GridLayout(30,1);
        titleBar.setSpacing(true);
        titleBar.setWidth("100%");
        titleBar.addStyleName("titleBarStyle");
        layout.addComponents(titleBar);

        Button boardButton = new Button("Boards");
        boardButton.setIcon(FontAwesome.BOOK);
        boardButton.setWidth("100%");
        boardButton.addStyleName("titleBarButtonStyle");
        titleBar.addComponent(boardButton,0,0,1,0);

        TextField searchArea = new TextField();
        searchArea.setWidth("100%");
        searchArea.setHeight("100%");
        searchArea.addStyleName("titleBarTextFieldStyle");
        titleBar.addComponent(searchArea,2,0,5,0);

        Label title = new Label("TRELLO");
        title.setWidth("100%");
        title.addStyleName("titleBarLabelStyle");
        titleBar.addComponent(title,6,0,23,0);

        Button addButton = new Button();
        addButton.setWidth("100%");
        addButton.setIcon(FontAwesome.PLUS);
        addButton.addStyleName("titleBarButtonStyle");
        titleBar.addComponent(addButton,24,0,24,0);

        Button userButton = new Button("WojtekP12");
        userButton.setWidth("100%");
        userButton.setIcon(FontAwesome.USER);
        userButton.addStyleName("titleBarButtonStyle");
        titleBar.addComponent(userButton,25,0,27,0);

        Button infoButton = new Button();
        infoButton.setWidth("100%");
        infoButton.setIcon(FontAwesome.INFO);
        infoButton.addStyleName("titleBarButtonStyle");
        titleBar.addComponent(infoButton,28,0,28,0);

        Button notificationButton = new Button();
        notificationButton.setWidth("100%");
        notificationButton.setIcon(FontAwesome.BELL);
        notificationButton.addStyleName("titleBarButtonStyle");
        titleBar.addComponent(notificationButton,29,0,29,0);

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

        layout.addComponents(tableLayout);
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addStyleName("mainContainerStyle");
        layout.setHeight("100%");
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
