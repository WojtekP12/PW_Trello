package org.test;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import javafx.scene.text.Font;
import org.controls.*;
import org.models.*;

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
		String username = String.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user"));
		
		/*if(username == "null")
			getUI().getPage().setLocation("/HomePage");*/
				
        final MainContainer layout = new MainContainer();
        setContent(layout.getContainer());

        TitleBar titleBar = new TitleBar(1,30);
        layout.addElement(titleBar.getTitleBar());
        layout.getContainer().setExpandRatio(titleBar.getTitleBar(), 0.2f);

        TitleBarButton boardButton = new TitleBarButton("Boards",FontAwesome.BOOK);
        titleBar.addElement(boardButton.getButton(),0,0,0,1);

        TitleBarSearchField searchField = new TitleBarSearchField();
        titleBar.addElement(searchField.getSerachField(),0,0,2,5);

        TitleBarTitleLabel titleLabel = new TitleBarTitleLabel("TRELLO");
        titleBar.addElement(titleLabel.getTitleLabel(),0,0,6,23);

        TitleBarButton addButton = new TitleBarButton("",FontAwesome.PLUS);
        titleBar.addElement(addButton.getButton(),0,0,24,24);

        TitleBarButton userButton = new TitleBarButton(username,FontAwesome.USER);
        titleBar.addElement(userButton.getButton(),0,0,25,27);
		userButton.getButton().addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("user", null);
				getUI().getPage().setLocation("/LoginPage");
			}
		});

        TitleBarButton infoButton = new TitleBarButton("", FontAwesome.INFO);
        titleBar.addElement(infoButton.getButton(),0,0,28,28);

        TitleBarButton notificationButton = new TitleBarButton("",FontAwesome.BELL);
        titleBar.addElement(notificationButton.getButton(),0,0,29,29);


        // *********listy*********
        HorizontalLayout tableLayout = new HorizontalLayout();
        tableLayout.setSizeUndefined();
        tableLayout.setSpacing(true);
        tableLayout.setMargin(true);

        Table table = new Table();
        table.setStyleName(Reindeer.LAYOUT_BLACK);
        table.setWidth(50,Unit.MM);
        table.setSelectable(true);

        Table table2 = new Table();
        table.setStyleName(Reindeer.LAYOUT_BLACK);
        table.setWidth(50,Unit.MM);
        table.setSelectable(true); 


		//*************************
		Board.testBoard();
		
		Board board = Board.boardsList.get(0);
		int n = board.size();
		for(int i=0;i<n;i++)
		{
			Table t = loadList(board.get(i));
			tableLayout.addComponent(t);
          //  tableLayout.setComponentAlignment(t, Alignment.TOP_CENTER);
            tableLayout.setExpandRatio(t, 1.0f);
		}
		//**********************

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
        layout.getContainer().setExpandRatio(tableLayout,0.8f);

        setContent(layout.getContainer());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
	
	Table loadList(List list)
	{
		Table table = new Table();
			table.setStyleName(Reindeer.LAYOUT_BLACK);
			table.setWidth(50,Unit.MM);
			table.setSelectable(true);
			table.addContainerProperty(list.getName(), Panel.class, null);
			
		int n = list.size();
		for(int i=0;i<n;i++)
		{
			Panel panel = loadCard(list.get(i));				
			table.addItem(new Object[] {panel},  i);
		}
		
		table.setPageLength(table.size());
		
		return table;
	}
	
	Panel loadCard(Card card)
	{
		Panel panel = new Panel(card.getName());
			panel.addStyleName(Reindeer.PANEL_LIGHT);
			panel.setSizeFull();
		
		addCardClickListener(panel, card);
		
		return panel;
	}
	
	void addCardClickListener(Panel panel, final Card card)
	{
		panel.addClickListener(new MouseEvents.ClickListener()
		{
			@Override
			public void click(MouseEvents.ClickEvent clickEvent)
			{
				Notification.show(card.getName());
				MySub sub = new MySub();
				UI.getCurrent().addWindow(sub);
			}
		});
	}
	
	
}
