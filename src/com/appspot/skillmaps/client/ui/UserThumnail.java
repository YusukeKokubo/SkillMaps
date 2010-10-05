package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
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


    public UserThumnail(final Login login, final Profile user, final PopupPanel userDialog) {
        initWidget(uiBinder.createAndBindUi(this));
        final Anchor close = new Anchor("close");
        close.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                userDialog.hide();
            }
        });

        id.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                userDialog.center();

                UserUI detail = null;
                detail = new UserUI(login, user);
                VerticalPanel p = new VerticalPanel();
                p.add(close);
                p.add(detail);

                userDialog.setWidget(p);
            }
        });

        id.setText(user.getId());
        id.setHref("#");
        name.setText(user.getName());
        icon.setUrl("/images/icon/" + user.getIconKeyString());
    }

}
