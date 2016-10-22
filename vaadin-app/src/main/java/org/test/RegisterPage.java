package org.test;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.controls.MainContainer;

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
        final MainContainer layout = new MainContainer();
        setContent(layout.getContainer());

        TextField loginField = new TextField("Login");
        layout.addElement(loginField);
        TextField email = new TextField("e-mail;");
        layout.addElement(email);
        layout.addElement(loginField);
        PasswordField passwordField = new PasswordField("Password");
        layout.addElement(passwordField);
        PasswordField repeatPasswordField = new PasswordField("Repeat Password");
        layout.addElement(repeatPasswordField);
        Button loginButton = new Button("Register");
        layout.addElement(loginButton);


    }

    @WebServlet(urlPatterns = "RegisterPage/*", name = "RegisterPageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RegisterPage.class, productionMode = false)
    public static class RegisterPageServlet extends VaadinServlet {
    }
}
