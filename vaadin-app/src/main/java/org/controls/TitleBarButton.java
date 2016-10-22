package org.controls;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * Created by wojciech.pelka on 22.10.2016.
 */
public class TitleBarButton
{
    private Button button;

    public TitleBarButton(String text, Resource icon)
    {
        button = new Button(text);
        button.setIcon(icon);
        button.setWidth("100%");
        button.addStyleName("titleBarButtonStyle");
    }

    public Button getButton()
    {
        return button;
    }
}
