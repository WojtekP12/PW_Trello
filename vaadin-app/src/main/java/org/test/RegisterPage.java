package org.test;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.controls.MainContainer;
import com.vaadin.ui.Button.ClickEvent;
import org.models.*;

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
		/*if(String.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user")) != "null")
			getUI().getPage().setLocation("/");*/
		
		
        Label registerLabel = new Label("Create account on TRELLO!");
        registerLabel.addStyleName("loginPageLabelStyle");

        TextField loginField = new TextField("Login");
        loginField.addStyleName("cap");
        loginField.addStyleName("loginPageTextFieldStyle");
        loginField.setWidth("60%");

        TextField email = new TextField("e-mail;");
        email.addStyleName("cap");
        email.addStyleName("loginPageTextFieldStyle");
        email.setWidth("60%");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.addStyleName("cap");
        passwordField.addStyleName("loginPageTextFieldStyle");
        passwordField.setWidth("60%");

        PasswordField repeatPasswordField = new PasswordField("Repeat Password");
        repeatPasswordField.addStyleName("cap");
        repeatPasswordField.addStyleName("loginPageTextFieldStyle");
        repeatPasswordField.setWidth("60%");

        Button registerButton = new Button("Register");
        registerButton.addStyleName("loginPageLoginButtonStyle");

        Button loginButton = new Button("Log In");
        loginButton.addStyleName("loginPageRegisterButtonStyle");

        HorizontalLayout buttons = new HorizontalLayout(registerButton,loginButton);
        buttons.addStyleName("loginPageButtonsContainerStyle");

        
        final VerticalLayout layout = new VerticalLayout(registerLabel,loginField,email,passwordField,repeatPasswordField,buttons);
        layout.setSpacing(true);
        layout.setMargin(new MarginInfo(true, true, true, false));
        layout.setSizeUndefined();
        layout.setComponentAlignment(registerLabel, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(loginField, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(email, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(repeatPasswordField, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);

        MainContainer container = new MainContainer();
        container.addElement(layout);
        container.getContainer().addStyleName("loginPageTextStyle");
        container.getContainer().setComponentAlignment(layout,Alignment.MIDDLE_CENTER);
        setContent(container.getContainer());
		
		// Button listeners
		
		loginButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				getUI().getPage().setLocation("/LoginPage");
			}
		});
		
		registerButton.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
			if(loginField.isEmpty() || email.isEmpty() || passwordField.isEmpty() || repeatPasswordField.isEmpty())
			{
				Notification.show("Some fields are empty.");
			} else
			{
				if(passwordField.getValue().equals(repeatPasswordField.getValue()))
				{
					if(!User.userExists(loginField.getValue()))
					{
						User u = new User(loginField.getValue(), passwordField.getValue(), email.getValue());
						User.users.add(u);
						VaadinService.getCurrentRequest().getWrappedSession().setAttribute("user", u.getUsername());
						getUI().getPage().setLocation("/BoardsPage");
					} else
						Notification.show("User exists!");
					
				} else
					Notification.show("Passwords do not match!");
			}
			}
		});


	}

    @WebServlet(urlPatterns = "RegisterPage/*", name = "RegisterPageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RegisterPage.class, productionMode = false)
    public static class RegisterPageServlet extends VaadinServlet {
    }

}
