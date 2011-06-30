package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.presenter.SkillOwnersActivity;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class SkillThumnail extends Composite {

    private static SkillThumnailUiBinder uiBinder = GWT
        .create(SkillThumnailUiBinder.class);

    interface SkillThumnailUiBinder extends UiBinder<Widget, SkillThumnail> {
    }

    @UiField
    Label skillName;

    @UiField
    Label skillPoint;

    @UiField
    UserThumnail profile;
    
    @UiField
    Anchor assertion;
    
    @UiField
    Label description;
    
    @UiField
    HorizontalPanel panel;
    
    @UiField
    Label count;
    
    @UiField
    SimplePanel agreeButton;

    Login login;

    SkillMapPopupPanel skillOwners;

    private final Provider<SkillOwnersActivity> skillOwnersProvider;

    private final EventBus eventBus;

    private final Provider<Anchor> permalinkProvider;

    private final Provider<UserThumnail> utProvider;

    private final Provider<SkillServiceAsync> serviceProvider;

    @Inject
    public SkillThumnail(Login login,
                         Provider<SkillOwnersActivity> skillOwnersProvider,
                         Provider<SkillServiceAsync> serviceProvider,
                         Provider<UserThumnail> utProvider,
                         @Named("skillOwnersPermalink") Provider<Anchor>  permalinkProvider,
                         EventBus eventBus) {
        this.login = login;
        this.skillOwnersProvider = skillOwnersProvider;
        this.utProvider = utProvider;
        this.permalinkProvider = permalinkProvider;
        this.eventBus = eventBus;
        this.skillOwners = new SkillMapPopupPanel();
        this.serviceProvider = serviceProvider;
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setSkill(final SkillAssertion sa){
        skillName.setText(sa.getSkill().getModel().getName());
        skillPoint.setText("(" + sa.getSkill().getModel().getPoint().toString() + ")");
        profile.setUser(sa.getSkill().getModel().getHolder().getModel());
        assertion.setHref(sa.getUrl());
        assertion.setText(sa.getUrl());
        description.setText(sa.getDescription());
        count.setText(sa.getAgrees().size() + "人がやるね！と言っています.");
        if (!sa.getSkill().getModel().isOwnBy(login.getProfile())) {
            if (sa.isAgreedBy(login.getProfile())) {
                agreeButton.setWidget(makeDisagreeButton(sa));
            } else {
                agreeButton.setWidget(makeAgreeButton(sa));
            }
            panel.add(agreeButton);
        }
    }

    @UiFactory
    UserThumnail makeProfile() {
        return utProvider.get();
    }

    private Anchor makeAgreeButton(final SkillAssertion sassertion) {
        Anchor agreedButton = new Anchor("やるね！");
        agreedButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                serviceProvider.get().agree(sassertion, new AsyncCallback<SkillAssertion>() {
                    @Override
                    public void onSuccess(SkillAssertion result) {
                        UiMessage.info("やるね！");
                        result.getSkill().setModel(sassertion.getSkill().getModel());
                        setSkill(result);
                    }
                    
                    @Override
                    public void onFailure(Throwable caught) {
                    }
                });
            }
        });
        return agreedButton;
    }

    private Anchor makeDisagreeButton(final SkillAssertion sassertion) {
        Anchor agreedButton = new Anchor("やるね！を取り消す.");
        agreedButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                serviceProvider.get().disagree(sassertion, new AsyncCallback<SkillAssertion>() {
                    @Override
                    public void onSuccess(SkillAssertion result) {
                        UiMessage.info("やるね！を取消しました.");
                        result.getSkill().setModel(sassertion.getSkill().getModel());
                        setSkill(result);
                    }
                    
                    @Override
                    public void onFailure(Throwable caught) {
                    }
                });
            }
        });
        return agreedButton;
    }
}
