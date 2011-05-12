package com.appspot.skillmaps.client.ui;


import com.appspot.skillmaps.client.display.SigninGuidanceDisplay;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SigninGuidance extends Composite implements SigninGuidanceDisplay{

    private static SigninGuidanceUiBinder uiBinder = GWT
        .create(SigninGuidanceUiBinder.class);

    interface SigninGuidanceUiBinder extends UiBinder<Widget, SigninGuidance> {
    }

    @UiField
    Label guidance;

    @UiField
    SigninAnchor signin;

    private Presenter presenter;

    @Inject
    public SigninGuidance(Login login) {
        initWidget(uiBinder.createAndBindUi(this));
        guidance.setText("SkillMapsは自分のスキルを他人が評価してくれるソーシャル他人評価サービスです.サインインすれば誰でもすぐに使えます!");
    }


    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }

}
