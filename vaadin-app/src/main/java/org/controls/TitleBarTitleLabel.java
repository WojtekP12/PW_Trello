package org.controls;

import com.vaadin.ui.Label;

/**
 * Created by wojciech.pelka on 22.10.2016.
 */
public class TitleBarTitleLabel {

    private Label titleLabel;

    public TitleBarTitleLabel(String text)
    {
        titleLabel = new Label(text);
        titleLabel.setWidth("100%");
        titleLabel.addStyleName("titleBarLabelStyle");
    }

    public Label getTitleLabel()
    {
        return titleLabel;
    }
}
