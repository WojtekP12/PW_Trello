package org.controls;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by wojciech.pelka on 22.10.2016.
 */
public class MainContainer
{
    private VerticalLayout layout;

    public MainContainer()
    {
        layout = new VerticalLayout();
        layout.addStyleName("mainContainerStyle");
        layout.setHeight("100%");
    }

    public VerticalLayout getContainer()
    {
        return layout;
    }

    public void addElement(Component c)
    {
        layout.addComponent(c);
    }

}
