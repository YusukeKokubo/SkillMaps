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

    public SkillAppealUI(SkillAppeal appeal) {
        initWidget(uiBinder.createAndBindUi(this));

        appealSkillName.setText(appeal.getAppealSkillName());
        description.setHTML("<pre>" + appeal.getDescription() + "</pre>");
        if (appeal.getUrl() != null && (!appeal.getUrl().isEmpty())) {
            url.setHref(appeal.getUrl());
            url.setText("link");
        }
    }

}
