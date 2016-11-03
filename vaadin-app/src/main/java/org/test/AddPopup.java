package org.test;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

/**
 * Created by wojciech.pelka on 02.11.2016.
 */
public class AddPopup extends Window
{
    Button addButton;
    TextField name;

    public AddPopup(String text)
    {
        super(text);
        center();

        GridLayout layout = new GridLayout(1,2);
        name = new TextField("Name");
        layout.addComponent(name,0,0,0,0);
        addButton = new Button("Add");
        layout.addComponent(addButton,0,1,0,1);
        layout.setSpacing(true);
        layout.setMargin(true);


        setContent(layout);
        setClosable(true);

    }

    public Button getAddButton()
    {
        return addButton;

    }

    public TextField getName()
    {
        return name;
    }


}
