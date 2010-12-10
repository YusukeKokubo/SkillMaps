package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.SkillAppealFormDisplay;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SkillAppealForm extends Composite implements
        SkillAppealFormDisplay, Editor<SkillAppeal> {

    private static SkillAppealFormUiBinder uiBinder =
        GWT.create(SkillAppealFormUiBinder.class);

    interface SkillAppealFormUiBinder extends UiBinder<Widget, SkillAppealForm> {
    }

    @UiField
    FormPanel form;

    @UiField
    TextBox appealSkillName;

    @UiField
    TextArea description;

    @UiField
    TextBox url;

    @UiField
    HorizontalPanel twitterPanel;

    @UiField
    @Editor.Ignore
    CheckBox sendTwitter;

    @UiField
    HorizontalPanel twitterGuidance;

    @UiField
    Button submit;

    private Presenter presenter;

    @Inject
    public SkillAppealForm(Login login) {
        initWidget(uiBinder.createAndBindUi(this));

        if (!login.isLoggedIn() || login.getProfile().getId() == null) {
            form.setVisible(false);
        } else {
            twitterPanel.setVisible(login.getProfile().isEnabledTwitter());
            if (!login.getProfile().isEnabledTwitter()) {
                twitterGuidance.setVisible(true);
                sendTwitter.setValue(false);
            }
        }
    }

    @UiHandler("submit")
    void onClick(ClickEvent e) {
        presenter.registSkillAppeal();
    }

    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public HasValue<Boolean> getSendTwitter() {
        return sendTwitter;
    }

    @Override
    public void initPresenter() {
        presenter.setupDisplay(this);
    }

}
