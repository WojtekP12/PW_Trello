package org.controls;

import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

/**
 * Created by wojciech.pelka on 22.10.2016.
 */
public class TitleBar
{
    private GridLayout titleBar;

    public TitleBar(int numberOfRows,int numberOfColumns)
    {
        titleBar = new GridLayout(numberOfColumns,numberOfRows);
        titleBar.setSpacing(true);
        titleBar.setWidth("100%");
        titleBar.addStyleName("titleBarStyle");
    }

    public GridLayout getTitleBar()
    {
        return titleBar;
    }

    public void addElement(Component c, int startRow, int endRow, int startCol, int endCol)
    {
        titleBar.addComponent(c,startCol,startRow,endCol,endRow);
    }
}
