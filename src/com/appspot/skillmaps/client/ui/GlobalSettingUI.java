package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.service.AdminService;
import com.appspot.skillmaps.client.service.AdminServiceAsync;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.shared.model.GlobalSetting;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class GlobalSettingUI extends Composite {

    private static GlobalSettingUIUiBinder uiBinder = GWT
        .create(GlobalSettingUIUiBinder.class);

    interface GlobalSettingUIUiBinder extends UiBinder<Widget, GlobalSettingUI> {
    }

    AdminServiceAsync service = GWT.create(AdminService.class);

    @UiField
    TextBox twitterConsumerKey;

    @UiField
    TextBox twitterConsumerSecret;

    @UiField
    TextBox twitterNotifierId;
    
    @UiField
    TextBox githubClientId;
    
    @UiField
    TextBox githubClientSecret;

    @UiField
    Button submit;

    GlobalSetting gs;

    public GlobalSettingUI() {
        initWidget(uiBinder.createAndBindUi(this));

        service.getGlobalSetting(new AsyncCallback<GlobalSetting>() {
            @Override
            public void onSuccess(GlobalSetting setting) {
                gs = setting;
                if (setting == null) {
                    gs = new GlobalSetting();
                } else {
                    twitterConsumerKey.setText(setting.getTwitterConsumerKey());
                    twitterConsumerSecret.setText(setting.getTwitterConsumerSecret());
                    githubClientId.setText(setting.getGithubClientId());
                    githubClientSecret.setText(setting.getGithubClientSecret());
                    if (setting.getTwitterNotifier().getModel() != null) {
                        twitterNotifierId.setText(setting.getTwitterNotifier().getModel().getId());
                    }
                }
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });

        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                gs.setTwitterConsumerKey(twitterConsumerKey.getText());
                gs.setTwitterConsumerSecret(twitterConsumerSecret.getText());
                gs.setGithubClientId(githubClientId.getText());
                gs.setGithubClientSecret(githubClientSecret.getText());
                service.putGlobalSetting(gs, twitterNotifierId.getText(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        UiMessage.info("更新しました!");
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        UiMessage.info(caught.getMessage());
                    }
                });
            }
        });
    }
}
