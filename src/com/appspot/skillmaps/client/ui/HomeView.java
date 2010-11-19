package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.HomeDisplay;
import com.appspot.skillmaps.client.presenter.ActivateGuidanceActivity;
import com.appspot.skillmaps.client.presenter.RecentAddedSkillsActivity;
import com.appspot.skillmaps.client.presenter.RecentEntriedUsersActivity;
import com.appspot.skillmaps.client.presenter.SigninGuidanceActivity;
import com.appspot.skillmaps.client.presenter.SkillAppealFormActivity;
import com.appspot.skillmaps.client.presenter.SkillAppealTimeLineActivity;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class HomeView extends Composite implements HomeDisplay {

    private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

    interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
    }

    @UiField
    AcceptsOneWidget homeHeaderPanel;

    @UiField
    AcceptsOneWidget skillAppealTimeLine;

    @UiField
    AcceptsOneWidget recentEntriedUsers;

    @UiField
    AcceptsOneWidget recentAddedSkills;

    private Presenter presenter;

    @Inject
    public HomeView(ContentsPanelProvider contentsPanelProvider,
                    EventBus eventBus,
                    SkillAppealTimeLineActivity skillAppealTimeLineActivity,
                    RecentEntriedUsersActivity recentEntriedUsersActivity,
                    RecentAddedSkillsActivity recentAddedSkillsActivity) {

        initWidget(uiBinder.createAndBindUi(this));
        contentsPanelProvider.get().start(homeHeaderPanel, eventBus);
        skillAppealTimeLineActivity.start(skillAppealTimeLine, eventBus);
        recentEntriedUsersActivity.start(recentEntriedUsers, eventBus);
        recentAddedSkillsActivity.start(recentAddedSkills, eventBus);

    }

    public static class ContentsPanelProvider implements Provider<AbstractActivity>{

        private final Login login;
        private final SkillAppealFormActivity skillAppealFormActivity;
        private final SigninGuidanceActivity signinGuidanceActivity;
        private final ActivateGuidanceActivity activateGuidanceActivity;

        @Inject
        public ContentsPanelProvider(Login login,
                                    SkillAppealFormActivity skillAppealFormActivity,
                                    SigninGuidanceActivity signinGuidanceActivity,
                                    ActivateGuidanceActivity activateGuidanceActivity){
            this.login = login;
            this.skillAppealFormActivity = skillAppealFormActivity;
            this.signinGuidanceActivity = signinGuidanceActivity;
            this.activateGuidanceActivity = activateGuidanceActivity;

        }

        @Override
        public AbstractActivity get() {
            if(!login.isLoggedIn()){
                return signinGuidanceActivity;
            }

            if(!login.getProfile().isActivate()){
                return activateGuidanceActivity;
            }
            return skillAppealFormActivity;
        }

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
