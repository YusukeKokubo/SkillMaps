package com.appspot.skillmaps.client.ui;

import java.util.HashMap;

import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserListUI extends Composite {

    private static UsersUiBinder uiBinder = GWT.create(UsersUiBinder.class);

    HashMap<String, UserUI> usersMap = new HashMap<String, UserUI>();

    interface UsersUiBinder extends UiBinder<Widget, UserListUI> {
    }

    @UiField
    VerticalPanel usersPanel;

    @UiField
    PopupPanel userDialog;

    @UiField
    ListBox layoutSelect;

    private final Login login;

    private final Profile[] users;

    public UserListUI(Login login, Profile[] users) {
        this.login = login;
        this.users = users;
        initWidget(uiBinder.createAndBindUi(this));

        layoutSelect.addItem("1");
        layoutSelect.addItem("2");
        layoutSelect.addItem("3");
        layoutSelect.addItem("4");
        layoutSelect.setSelectedIndex(0);
        reloadUsersPanel(login, users, layoutSelect.getSelectedIndex() + 1);
    }

    protected void reloadUsersPanel(final Login login, Profile[] users, int viewColumn) {
        final Anchor close = new Anchor("close");
        close.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                userDialog.hide();
            }
        });

        HorizontalPanel hPanel = null;
        int column = 0;
        int thumnailWidth = RootPanel.get("users").getOffsetWidth() / viewColumn;
        for (final Profile user : users) {

            if(column == 0){
                hPanel = new HorizontalPanel();
            }
            column++;

            FocusPanel panel = new FocusPanel();
            panel.setWidth(thumnailWidth + "px");
            panel.add(new UserThumnail(login, user));

            panel.addMouseOverHandler(new MouseOverHandler() {
                @Override
                public void onMouseOver(MouseOverEvent event) {
                    userDialog.center();

                    UserUI detail = null;
                    if (usersMap.containsKey(user.getUserEmail())) {
                        detail = usersMap.get(user.getUserEmail());
                    } else {
                        detail = new UserUI(login, user);
                        usersMap.put(user.getUserEmail(), detail);
                    }
                    VerticalPanel p = new VerticalPanel();
                    p.add(close);
                    p.add(detail);

                    userDialog.setWidget(p);
               }
            });

            if(column < viewColumn){
                hPanel.add(panel);
            }else if(column == viewColumn){
                hPanel.add(panel);
                usersPanel.add(hPanel);
                column = 0;
                hPanel = null;
            }
        }

        if(hPanel != null){

            usersPanel.add(hPanel);
        }
    }

    @UiHandler("layoutSelect")
    void onLayoutSelectChange(ChangeEvent ce){
        usersPanel.clear();
        reloadUsersPanel(login, users, layoutSelect.getSelectedIndex() + 1);
    }

}
