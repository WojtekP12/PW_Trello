package org.test;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.controls.MainContainer;
import org.controls.TitleBar;
import org.controls.TitleBarButton;
import org.controls.TitleBarTitleLabel;
import org.models.*;

import javax.servlet.annotation.WebServlet;

/**
 * Created by wojciech.pelka on 19.10.2016.
 */
@Theme("mytheme")
public class LoginPage extends UI {
    @Override
    protected void init(VaadinRequest request)
    {
		/*if(String.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user")) != "null")
			getUI().getPage().setLocation("/");*/
		
		
        Label loginLabel = new Label("Log in to TRELLO!");
        loginLabel.addStyleName("loginPageLabelStyle");

        TextField loginField = new TextField("Login");
        loginField.addStyleName("cap");
        loginField.addStyleName("loginPageTextFieldStyle");
        loginField.setWidth(60,Unit.PERCENTAGE);

        PasswordField passwordField = new PasswordField("Password");
        passwordField.addStyleName("cap");
        passwordField.addStyleName("loginPageTextFieldStyle");
        passwordField.setWidth(60,Unit.PERCENTAGE);

        Button loginButton = new Button("Log In");
        loginButton.addStyleName("loginPageLoginButtonStyle");
        //loginButton.setWidth(50,Unit.PERCENTAGE);

        Button registerButton = new Button("Register");
        registerButton.addStyleName("loginPageRegisterButtonStyle");
        //registerButton.setWidth(50,Unit.PERCENTAGE);

        HorizontalLayout buttons = new HorizontalLayout(loginButton,registerButton);
        buttons.addStyleName("loginPageButtonsContainerStyle");
        //buttons.setWidth(60,Unit.PERCENTAGE);

        final VerticalLayout layout = new VerticalLayout(loginLabel,loginField,passwordField,buttons);
        layout.setSpacing(true);
        layout.setMargin(new MarginInfo(true, true, true, false));
        layout.setSizeUndefined();
        layout.setComponentAlignment(loginLabel, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(loginField, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);

        MainContainer container = new MainContainer();
        container.addElement(layout);
        container.getContainer().addStyleName("loginPageTextStyle");
        container.getContainer().setComponentAlignment(layout,Alignment.MIDDLE_CENTER);
        setContent(container.getContainer());
		
		addButtonClickListeners(loginButton,registerButton,loginField,passwordField);
		User.testUsers();
    }

    @WebServlet(urlPatterns = "LoginPage/*", name = "LoginPageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = LoginPage.class, productionMode = false)
    public static class LoginPageServlet extends VaadinServlet {
    }
	
	void addButtonClickListeners(Button login, Button register, TextField user, PasswordField password)
	{
		login.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				User u = null;
				
				int n = User.users.size();
				for(int i=0;i<n;i++)
				{
					if(User.users.get(i).username.equals(user.getValue()))
						u = User.users.get(i);
				}
				
				if (u==null)
				{
					Notification.show("User not found: "+user.getValue());
				} else
				{
					if(password.getValue().equals(u.password))
					{
						VaadinService.getCurrentRequest().getWrappedSession().setAttribute("user", user.getValue());
						getUI().getPage().setLocation("/");
					} else
					{
						Notification.show("Wrong password!");
					}
				}
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