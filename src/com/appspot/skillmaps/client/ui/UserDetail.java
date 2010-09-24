package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UserDetail extends Composite {

    private static UserDetailUiBinder uiBinder = GWT
        .create(UserDetailUiBinder.class);

    @UiField
    Label selfIntroduction;

    @UiField
    Anchor id;

    @UiField
    Label name;

    @UiField
    Image icon;

    interface UserDetailUiBinder extends UiBinder<Widget, UserDetail> {
    }

    public UserDetail(Profile p) {
        initWidget(uiBinder.createAndBindUi(this));

        id.setText(p.getId());
        name.setText(p.getName());
        icon.setUrl("/images/icon/" + p.getIconKeyString());
        selfIntroduction.setText(p.getSelfIntroduction());
    }

}
