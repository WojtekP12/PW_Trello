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
public class RegisterPage extends UI
{
    @Override
    protected void init(VaadinRequest request)
    {
        final VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("mainContainerStyle");
        layout.setHeight("100%");
        setContent(layout);

        TextField loginField = new TextField("Login");
        layout.addComponent(loginField);
        TextField email = new TextField("e-mail;");
        layout.addComponent(email);
        layout.addComponent(loginField);
        PasswordField passwordField = new PasswordField("Password");
        layout.addComponent(passwordField);
        PasswordField repeatPasswordField = new PasswordField("Repeat Password");
        layout.addComponent(repeatPasswordField);
        Button loginButton = new Button("Register");
        layout.addComponent(loginButton);
    }

    @WebServlet(urlPatterns = "RegisterPage/*", name = "RegisterPageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RegisterPage.class, productionMode = false)
    public static class RegisterPageServlet extends VaadinServlet {
    }
}
