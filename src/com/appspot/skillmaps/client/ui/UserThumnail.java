package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserThumnail extends Composite {

    private static UserUiBinder uiBinder = GWT.create(UserUiBinder.class);

    interface UserUiBinder extends UiBinder<Widget, UserThumnail> {
    }

    @UiField
    Anchor id;

    @UiField
    Label name;

    @UiField
    Image icon;

    @UiField
    FocusPanel panel;

    @UiField
    PopupPanel userDialog;

    public UserThumnail(Login login, Profile user) {
        initWidget(uiBinder.createAndBindUi(this));

        id.setText(user.getId());
        id.setHref("/user.html?id=" + user.getId());
        name.setText(user.getName());
        icon.setUrl("/images/icon/" + user.getIconKeyString());

        final UserUI detail = new UserUI(login, user);
        panel.addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                userDialog.setWidget(detail);
                userDialog.center();
            }
        });

        panel.addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                userDialog.hide();
            }
        });
    }

}
