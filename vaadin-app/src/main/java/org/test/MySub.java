package org.test;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Created by Adam Redliński on 2016-10-20.
 */
public class MySub extends Window {
    public MySub() {
        super(); // Set window caption
        center();

        // Some basic content for the window
        VerticalLayout content = new VerticalLayout();
        content.addComponent(new Label("w liście "));
        content.setMargin(true);
        setContent(content);

        setClosable(true);

        // Trivial logic for closing the sub-window
        Button ok = new Button("OK");
        ok.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                close(); // Close the sub-window
            }
        });
        content.addComponent(ok);
    }
}