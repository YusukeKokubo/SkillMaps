package com.appspot.skillmaps.client.ui;


import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class Header extends Composite {

    private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);

    public interface Style extends CssResource {
        @ClassName("sign-in")
        String signIn();

        @ClassName("sign-out")
        String signOut();

        String loading();
    }

    @UiField
    Style style;
    
    @UiField
    Image icon;

    @UiField
    Label nickname;

    @UiField
    Label myPageDelimiter;

    @UiField
    Label friendsDelimiter;

    @UiField
    Hyperlink myPageLink;

    @UiField
    Hyperlink friendsLink;

    @UiField
    SigninAnchor signin;

    @UiField
    Anchor signout;

    @UiField
    Panel signPanel;

    interface HeaderUiBinder extends UiBinder<Widget, Header> {
    }

    public Header(Login login) {
        initWidget(uiBinder.createAndBindUi(this));

        if (login == null) {
            nickname.setText("ロード中...");
            signPanel.addStyleName(style.loading());
            return;
        }

        if (login.isLoggedIn()) {
            nickname.setText(login.getNickname());
            signout.setHref(login.getLogoutUrl());
            String iconUrl = login.getProfile().getIconUrl();
            if (iconUrl != null) {
                icon.setUrl(iconUrl);
            } else {
                icon.setResource(Resources.INSTANCE.noimage());
            }

            friendsLink.setVisible(true);
            myPageLink.setVisible(true);
            friendsDelimiter.setVisible(true);
            myPageDelimiter.setVisible(true);

            signPanel.removeStyleName(style.loading());
            signPanel.addStyleName(style.signIn());
        } else {
            nickname.setText("");
            signout.setHref(login.getLogoutUrl());

            friendsLink.setVisible(false);
            myPageLink.setVisible(false);
            friendsDelimiter.setVisible(false);
            myPageDelimiter.setVisible(false);

            signPanel.removeStyleName(style.loading());
            signPanel.addStyleName(style.signOut());
        }
    }
}
