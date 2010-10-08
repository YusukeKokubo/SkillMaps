package com.appspot.skillmaps.client.ui;

import java.util.Arrays;
import java.util.Date;

import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class ProfileUI extends Composite {

    private static AccountConfigUiBinder uiBinder = GWT
        .create(AccountConfigUiBinder.class);

    @UiField
    Button submit;

    @UiField
    TextBox id;

    @UiField
    TextBox name;

    @UiField
    TextArea selfIntroduction;

    @UiField
    TextBox profileUrl1;

    @UiField
    TextBox profileUrl2;

    @UiField
    Label lblTwitterEnabled;

    @UiField
    Image icon;

    @UiField
    FileUpload iconUploder;

    @UiField
    Button iconSubmit;

    @UiField
    FormPanel form;

    @UiField
    TabLayoutPanel tabPanel;

    @UiField
    VerticalPanel profileTwitter;

    @UiField
    CheckBox allowFromTwitterNotifier;

    @UiField
    Button twitterProfileSubmit;

    private Login login;

    private final AccountServiceAsync service = GWT
        .create(AccountService.class);

    private final class ProfileSubmit implements ClickHandler {
        private final Profile p;

        private ProfileSubmit(Profile p) {
            this.p = p;
        }

        @Override
        public void onClick(ClickEvent event) {
            p.setId(id.getText());
            p.setName(name.getText());
            p.setSelfIntroduction(selfIntroduction.getText());
            p.setProfileUrl1(profileUrl1.getText());
            p.setProfileUrl2(profileUrl2.getText());
            p.setAllowFromTwitterNotifier(allowFromTwitterNotifier.getValue());
            service.putProfile(p, new AsyncCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    Window.alert("更新しました!!");
                    Window.Location.reload();
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage() + "\n" + Arrays.toString(caught.getStackTrace()));
                }
            });
        }
    }

    interface AccountConfigUiBinder extends UiBinder<Widget, ProfileUI> {
    }

    public ProfileUI(final Login login) {
        this.login = login;
        initWidget(uiBinder.createAndBindUi(this));
        final Profile p = login.getProfile();
        id.setReadOnly(p.getId() == null ? false : true);
        id.setText(p.getId());
        name.setText(p.getName());
        selfIntroduction.setText(p.getSelfIntroduction());
        profileUrl1.setText(p.getProfileUrl1());
        profileUrl2.setText(p.getProfileUrl2());
        allowFromTwitterNotifier.setValue(p.getAllowFromTwitterNotifier() != null ? p.getAllowFromTwitterNotifier() : false);
        if (!p.isActivate()) {
            tabPanel.remove(0);
        }
        if (p.isEnabledTwitter()){
            lblTwitterEnabled.setText("有効");
            profileTwitter.setVisible(true);
        }

        if (p.getIconKey() != null) icon.setUrl("/images/icon/" + p.getIconKeyString());
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
        iconUploder.setName("uploadFormElement");

        submit.addClickHandler(new ProfileSubmit(p));
        twitterProfileSubmit.addClickHandler(new ProfileSubmit(p));

        iconUploder.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                form.setAction("/IconUpload?file=" + iconUploder.getFilename());
            }
        });

        iconSubmit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                form.submit();
            }
        });

        form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(SubmitCompleteEvent event) {
                icon.setUrl(icon.getUrl() + "?token=" + new Date().getTime());
                Window.alert("更新しました!!");
            }
        });
    }

    @UiFactory
    UserUI makeUserUI() {
        return new UserUI(login, login.getProfile());
    }

}
