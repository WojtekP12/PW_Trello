package org.test;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

/**
 * Created by wojciech.pelka on 22.10.2016.
 */
@Theme("mytheme")
public class HomePage extends UI{
    @Override
    protected void init(VaadinRequest request)
    {
        final VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("mainContainerStyle");
        layout.setHeight("100%");
        setContent(layout);

        GridLayout titleBar = new GridLayout(30,1);
        titleBar.setSpacing(true);
        titleBar.setWidth("100%");
        titleBar.addStyleName("titleBarStyle");
        layout.addComponents(titleBar);

        Label titleLabel = new Label("TRELLO");
        titleBar.addComponent(titleLabel);
        Button loginButton = new Button("Login");
        titleBar.addComponent(loginButton);
        Button registerButton = new Button("Register");
        titleBar.addComponent(registerButton);
    }

    @WebServlet(urlPatterns = "HomePage/*", name = "HomePageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = HomePage.class, productionMode = false)
    public static class HomePageServlet extends VaadinServlet {
    }
}
