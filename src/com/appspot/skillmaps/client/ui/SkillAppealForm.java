package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.SkillAppealFormDisplay;
import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SkillAppealForm extends Composite implements SkillAppealFormDisplay{

    private static SkillAppealFormUiBinder uiBinder = GWT
        .create(SkillAppealFormUiBinder.class);

    private final SkillServiceAsync service = GWT.create(SkillService.class);

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
        }else{
            twitterPanel.setVisible(login.getProfile().isEnabledTwitter());
            if (!login.getProfile().isEnabledTwitter()) {
                twitterGuidance.setVisible(true);
                sendTwitter.setValue(false);
            }
        }
    }

    @UiHandler("submit")
    void onClick(ClickEvent e){
        if (appealSkillName.getText().isEmpty()) {
            Window.alert("アピールするスキルを入力してください");
            return;
        }
        if (description.getText().isEmpty()) {
            Window.alert("アピール文を入力してください");
            return;
        }
        SkillAppeal appeal = new SkillAppeal();
        appeal.setAppealSkillName(appealSkillName.getText());
        appeal.setDescription(description.getText());
        appeal.setUrl(url.getText());

        service.putSkillAppeal(appeal, sendTwitter.getValue(), new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Window.alert("アピールしました!");
                Window.Location.reload();
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
            }
        });
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }

}
