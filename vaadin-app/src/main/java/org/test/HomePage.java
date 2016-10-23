package org.test;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import javafx.scene.text.Font;
import org.controls.MainContainer;
import org.controls.TitleBar;
import org.controls.TitleBarButton;
import org.controls.TitleBarTitleLabel;

import javax.servlet.annotation.WebServlet;

/**
 * Created by wojciech.pelka on 22.10.2016.
 */
@Theme("mytheme")
public class HomePage extends UI{
    @Override
    protected void init(VaadinRequest request)
    {
        final MainContainer layout = new MainContainer();
        setContent(layout.getContainer());

        TitleBar titleBar = new TitleBar(1,30);
        layout.addElement(titleBar.getTitleBar());

        TitleBarButton loginButton = new TitleBarButton("Login", FontAwesome.SIGN_IN);
        titleBar.addElement(loginButton.getButton(),0,0,0,3);

        TitleBarButton registerButton = new TitleBarButton("Register",FontAwesome.USER);
        titleBar.addElement(registerButton.getButton(),0,0,4,7);

        TitleBarTitleLabel titleLabel = new TitleBarTitleLabel("TRELLO");
        titleBar.addElement(titleLabel.getTitleLabel(),0,0,8,20);
		
		addButtonClickListeners(loginButton.getButton(), registerButton.getButton());


        Label welcome = new Label("WELCOME IN TRELLO");
        welcome.addStyleName("titleBarLabelStyle");

        layout.addElement(welcome);

    }

    @WebServlet(urlPatterns = "HomePage/*", name = "HomePageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = HomePage.class, productionMode = false)
    public static class HomePageServlet extends VaadinServlet {
    }
	
	void addButtonClickListeners(Button login, Button register)
	{
		login.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				getUI().getPage().setLocation("/LoginPage");
			}
		});
		
		register.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				getUI().getPage().setLocation("/RegisterPage");
			}
		});
	}
	
}
