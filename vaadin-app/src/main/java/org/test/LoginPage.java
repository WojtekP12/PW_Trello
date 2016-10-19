package org.test;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;

/**
 * Created by wojciech.pelka on 19.10.2016.
 */
@Theme("mytheme")
public class LoginPage extends UI {
    @Override
    protected void init(VaadinRequest request)
    {
        final VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("mainContainerStyle");
        layout.setHeight("100%");
        setContent(layout);
    }

    @WebServlet(urlPatterns = "LoginPage/*", name = "LoginPageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = LoginPage.class, productionMode = false)
    public static class LoginPageServlet extends VaadinServlet {
    }
}
