package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillAppealUI extends Composite {

    private static SkillAppealUIUiBinder uiBinder = GWT
        .create(SkillAppealUIUiBinder.class);

    interface SkillAppealUIUiBinder extends UiBinder<Widget, SkillAppealUI> {
    }

    @UiField
    Label appealSkillName;

    @UiField
    HTML description;

    @UiField
    Anchor url;

    @UiField(provided=true)
    UserThumnail userThumnail;

    @Inject
    public SkillAppealUI(Provider<UserThumnail> utProvider) {
        userThumnail = utProvider.get();
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setAppeal(SkillAppeal appeal){
        userThumnail.setUser(appeal.getProfile());
        appealSkillName.setText(appeal.getAppealSkillName());
        description.setHTML("<p>" + appeal.getDescription().replaceAll("\\n", "<br />") + "</p>");
        if (appeal.getUrl() != null && (!appeal.getUrl().isEmpty())) {
            url.setHref(appeal.getUrl());
            url.setText("link");
        }
    }
}
