package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.bundle.Resources;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

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

    private final Login login;

    @Inject
    public UserThumnail(Login login) {
        this.login = login;
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setUser(final Profile user){
        id.setText(user.getId());
        name.setText(user.getName());
        if(user.getHasIcon() != null && user.getHasIcon()){
            icon.setUrl("/images/icon/" + user.getIconKeyString());
        } else {
            icon.setResource(Resources.INSTANCE.noimage());
        }

        id.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SkillMapPopupPanel userDialog = new SkillMapPopupPanel();
                userDialog.setAutoHideEnabled(true);
                //TODO UserUIをProvider get
                UserUI detail = new UserUI(login, user);
                userDialog.setContents(detail);
                userDialog.center();
            }
        });
    }

}
