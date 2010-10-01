package com.appspot.skillmaps.client.ui;

import java.util.HashMap;

import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserListUI extends Composite {

    private static UsersUiBinder uiBinder = GWT.create(UsersUiBinder.class);

    interface UsersUiBinder extends UiBinder<Widget, UserListUI> {
    }

    @UiField
    VerticalPanel usersPanel;

    @UiField
    PopupPanel userDialog;

    HashMap<String, UserUI> usersMap = new HashMap<String, UserUI>();

    public UserListUI(final Login login, Profile[] users) {
        initWidget(uiBinder.createAndBindUi(this));

        final Anchor close = new Anchor("close");
        close.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                userDialog.hide();
            }
        });

        for (final Profile user : users) {
            FocusPanel panel = new FocusPanel();
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
            usersPanel.add(panel);
        }
    }

}
