package com.appspot.skillmaps.client.ui;


import com.appspot.skillmaps.client.display.ActivateGuidanceDisplay;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ActivateGuidance extends Composite implements ActivateGuidanceDisplay{

    private static SigninGuidanceUiBinder uiBinder = GWT
        .create(SigninGuidanceUiBinder.class);

    interface SigninGuidanceUiBinder extends UiBinder<Widget, ActivateGuidance> {
    }

    @UiField
    Label guidance;

    @UiField
    Anchor signin;

    private Presenter presenter;


    @Inject
    public ActivateGuidance(Login login) {
        initWidget(uiBinder.createAndBindUi(this));
        guidance.setText("サービスにサインインしています. プロフィールを入力してアカウントを有効にしてください.");
        signin.setHref("/mypage.html");
    }


    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }

}
