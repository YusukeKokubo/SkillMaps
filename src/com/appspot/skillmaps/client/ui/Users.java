package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Users extends Composite {

    private static UsersUiBinder uiBinder = GWT.create(UsersUiBinder.class);

    interface UsersUiBinder extends UiBinder<Widget, Users> {
    }

    @UiField
    VerticalPanel usersPanel;

    @UiField
    PopupPanel userDialog;

    public Users(Login login, Profile[] users) {
        initWidget(uiBinder.createAndBindUi(this));

        for (Profile user : users) {
            FocusPanel panel = new FocusPanel();
            panel.add(new UserThumnail(login, user));

            final UserUI detail = new UserUI(login, user);
            panel.addMouseOverHandler(new MouseOverHandler() {
                @Override
                public void onMouseOver(MouseOverEvent event) {
                    userDialog.setWidget(detail);
                    userDialog.center();
                }
            });
            usersPanel.add(panel);
        }
    }

}
