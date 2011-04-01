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
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class HomeView extends Composite implements HomeDisplay {

    private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

    interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
    }

    @UiField
    DecoratedTabBar menuBar;

    @UiField
    SimplePanel contentsPanel;

    @UiField
    AcceptsOneWidget homeHeaderPanel;

    SimplePanel skillAppealTimeLine = new SimplePanel();

    SimplePanel recentEntriedUsers = new SimplePanel();

    SimplePanel recentAddedSkills = new SimplePanel();

    private Presenter presenter;

    public final Login login;

    @Inject
    public HomeView(ContentsPanelProvider contentsPanelProvider,
                    EventBus eventBus,
                    SkillAppealTimeLineActivity skillAppealTimeLineActivity,
                    RecentEntriedUsersActivity recentEntriedUsersActivity,
                    RecentAddedSkillsActivity recentAddedSkillsActivity,
                    Login login) {

        this.login = login;
        initWidget(uiBinder.createAndBindUi(this));

        menuBar.addTab("Appeal");
        menuBar.addTab("New Users");
        menuBar.addTab("New Skils");
        menuBar.addSelectionHandler(new SelectionHandler<Integer>() {

            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                contentsPanel.clear();
                switch (event.getSelectedItem()) {
                case 0:
                    contentsPanel.setWidget(skillAppealTimeLine);
                    break;
                case 1:
                    contentsPanel.setWidget(recentEntriedUsers);
                    break;
                case 2:
                    contentsPanel.setWidget(recentAddedSkills);
                    break;
                }
            }
        });
        menuBar.selectTab(0, false);
        contentsPanel.add(skillAppealTimeLine);

        contentsPanelProvider.get().start(homeHeaderPanel, eventBus);
        skillAppealTimeLineActivity.start(skillAppealTimeLine, eventBus);
        recentEntriedUsersActivity.start(recentEntriedUsers, eventBus);
        recentAddedSkillsActivity.start(recentAddedSkills, eventBus);

    }

    public static class ContentsPanelProvider implements Provider<AbstractActivity>{

        private final SkillAppealFormActivity skillAppealFormActivity;
        private final SigninGuidanceActivity signinGuidanceActivity;
        private final ActivateGuidanceActivity activateGuidanceActivity;
        private final Login login;
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
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }
}
