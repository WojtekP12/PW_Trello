package org.test;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.models.Card;

import static com.vaadin.server.FontAwesome.BOLD;

/**
 * Created by Adam Redliński on 2016-10-20.
 */
public class MySub extends Window {
    private Card card;
    private GridLayout container;

    public MySub() {
        super("ok"); // Set window caption
        center();

        HorizontalLayout subWindowContainer = new HorizontalLayout();
        GridLayout content = new GridLayout(10,10);

        content.addComponent(new Label("w liście "),1,1);

        Button button = new Button("lista1");
        button.setStyleName(Reindeer.BUTTON_LINK);
        content .addComponent(button,2,1);
        content.setComponentAlignment(button,Alignment.TOP_LEFT);

        Button editDescription = new Button("Edytuj opis");
        editDescription.setStyleName(Reindeer.BUTTON_LINK);
        content.addComponent(editDescription,1,3);

        Label addCommentLabel = new Label("Dodaj komentarz");
        content.addComponent(addCommentLabel,1,4);

        RichTextArea commentArea = new RichTextArea();
        commentArea.setHeight(60f,Unit.MM);
        content.addComponent(commentArea,1,5,9,5);

        Button sendComment = new Button("Wyślij");
        sendComment.setDisableOnClick(true);
        content.addComponent(sendComment,1,6);

        Label activityLabel = new Label("Aktywność");
        content.addComponent(activityLabel,0,7);

        Button detailsButton = new Button("Pokaż szczegóły");
        detailsButton.setStyleName(Reindeer.BUTTON_LINK);
        content.addComponent(detailsButton,9,7);

        Panel activityPanel = new Panel();
        activityPanel.setHeight(100f, Unit.MM);
        content.addComponent(activityPanel,1,8,9,8);

        content.setMargin(true);

        VerticalLayout rightMenu = new VerticalLayout();
        rightMenu.setSpacing(true);

        Label addLabel = new Label("Dodaj");
        rightMenu.addComponent(addLabel);

        Button membersButton = new Button("Członkowie");
        rightMenu.addComponent(membersButton);

        Button labelsButton = new Button("Etykiety");
        rightMenu.addComponent(labelsButton);

        Button taskListButton = new Button("Lista zadań");
        rightMenu.addComponent(taskListButton);

        Button termsButton = new Button("Termin");
        rightMenu.addComponent(membersButton);

        Button attatchmentButton = new Button("Załącznik");
        rightMenu.addComponent(attatchmentButton);

        Label activitiesLabel = new Label("Działania");
        rightMenu.addComponent(activitiesLabel);

        Button moveButton = new Button("Przenieś");
        rightMenu.addComponent(moveButton);

        Button copyButton = new Button("Kopiuj");
        rightMenu.addComponent(copyButton);

        Button subscribeButton = new Button("Subskrybuj");
        rightMenu.addComponent(subscribeButton);

        Button archiveButton = new Button("Zarchiwizuj");
        rightMenu.addComponent(membersButton);

        Button shareButton = new Button("Udostępnij i więcej...");
        shareButton.addStyleName(Reindeer.BUTTON_LINK);
        rightMenu.addComponent(shareButton);

        subWindowContainer.addComponents(content, rightMenu);
        setContent(subWindowContainer);
        setClosable(true);

    }

    public void init(Card card)
    {


    }
    public void setCard(Card card) {this.card = card; }

    public Card getCard() {return this.card; }

    public void setContainer(GridLayout container) {this.container = container; }

    public GridLayout getContainer() {return this.container; }
}