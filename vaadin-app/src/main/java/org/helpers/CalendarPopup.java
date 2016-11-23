package org.helpers;

import com.vaadin.ui.*;

import java.util.Date;

/**
 * Created by Adam Redliński on 2016-11-24.
 */
public class CalendarPopup extends Window {
    Calendar cal;

    public CalendarPopup(String text)
    {
        super(text);
        center();

        GridLayout layout = new GridLayout(1,2);
        cal = new Calendar("My Calendar");
        cal.setWidth("600px");
        cal.setHeight("300px");
        cal.setStartDate(new Date());
        cal.setEndDate(new Date());

        layout.addComponent(cal);
        setContent(layout);
        setClosable(true);

    }
}
