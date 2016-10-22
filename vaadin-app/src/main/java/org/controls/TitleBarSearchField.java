package org.controls;

import com.vaadin.ui.TextField;

/**
 * Created by wojciech.pelka on 22.10.2016.
 */
public class TitleBarSearchField
{
    private TextField searchField;

    public TitleBarSearchField()
    {
        searchField = new TextField();
        searchField.setWidth("100%");
        searchField.setHeight("100%");
        searchField.addStyleName("titleBarTextFieldStyle");
    }

    public TextField getSerachField()
    {
        return searchField;
    }
}
