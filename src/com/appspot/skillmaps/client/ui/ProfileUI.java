package com.appspot.skillmaps.client.ui;

import java.util.Date;

import com.appspot.skillmaps.client.display.MyPageDisplay;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ProfileUI extends Composite implements MyPageDisplay , Editor<Profile>{

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
    @Editor.Ignore
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

    @UiField
    CheckBox allowFromMailNotifier;

    @UiField
    Button mailNotifierSubmit;

    private Login login;

    private Presenter presenter;

    private final Provider<UserUI> userUiProvider;

    interface AccountConfigUiBinder extends UiBinder<Widget, ProfileUI> {
    }

    @Inject
    public ProfileUI(final Login login,
                     Provider<UserUI> userUiProvider) {
        this.login = login;
        this.userUiProvider = userUiProvider;
        initWidget(uiBinder.createAndBindUi(this));
        final Profile p = login.getProfile();
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
    }

    @UiHandler("form")
    public void onFormSubmitComplete(SubmitCompleteEvent e){
        icon.setUrl(icon.getUrl() + "?token=" + new Date().getTime());
        UiMessage.info("更新しました!!");
    }

    @UiHandler("iconUploder")
    public void onIconUploderChange(ChangeEvent e){
        form.setAction("/IconUpload?file=" + iconUploder.getFilename());
    }

    @UiHandler("iconSubmit")
    public void onIconSubmitClick(ClickEvent e){
        form.submit();
    }

    @UiHandler({"submit" , "twitterProfileSubmit"})
    public void onProfileSubmit(ClickEvent e){
        presenter.registProfile();
    }
    
    @UiHandler({"submit" , "mailNotifierSubmit"})
    public void onMailNotifierSubmit(ClickEvent e){
        presenter.registProfile();
    }

    @UiFactory
    UserUI makeUserUI() {
        UserUI userUIDisplay = userUiProvider.get();
        userUIDisplay.setProfile(login.getProfile());
        return userUIDisplay;
    }

    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }
}
