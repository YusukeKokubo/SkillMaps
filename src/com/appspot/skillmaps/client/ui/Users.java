package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Users extends Composite {

    private static UsersUiBinder uiBinder = GWT.create(UsersUiBinder.class);

    interface UsersUiBinder extends UiBinder<Widget, Users> {
    }

    @UiField
    VerticalPanel usersPanel;

    public Users(Profile[] users) {
        initWidget(uiBinder.createAndBindUi(this));

        for (Profile user : users) {
            usersPanel.add(new UserThumnail(user));
        }
    }

}
