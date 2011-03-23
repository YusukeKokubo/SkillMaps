package com.appspot.skillmaps.client.ui;


import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class Header extends Composite {

    private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);

    public interface Style extends CssResource {
        @ClassName("sign-in")
        String signIn();
    }

    @UiField
    Style style;

    interface HeaderUiBinder extends UiBinder<Widget, Header> {
    }

    public Header(Login login) {
        initWidget(uiBinder.createAndBindUi(this));

        if (login == null) {
            nickname.setText("未ログイン");
            signin.setEnabled(false);
            signout.setEnabled(false);
            return;
        }

        if (login.isLoggedIn()) {
            nickname.setText(login.getNickname());
            signin.setEnabled(false);
            signout.setHref(login.getLogoutUrl());
            signPanel.addStyleName(style.signIn());
        } else {
            nickname.setText("");
            signin.setHref(login.getLoginUrl());
            signout.setHref(login.getLogoutUrl());
        }
    }

    @UiField
    Label nickname;

    @UiField
    Anchor signin;

    @UiField
    Anchor signout;

    @UiField
    Panel signPanel;

}
