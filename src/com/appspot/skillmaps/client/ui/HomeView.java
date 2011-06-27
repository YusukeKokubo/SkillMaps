package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.HomeDisplay;
import com.appspot.skillmaps.client.presenter.RecentAddedSkillsActivity;
import com.appspot.skillmaps.client.presenter.SkillAppealTimeLineActivity;
import com.appspot.skillmaps.client.presenter.TimeLineActivity;
import com.appspot.skillmaps.shared.model.Login;
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

    SimplePanel timeLine = new SimplePanel();

    SimplePanel skillAppealTimeLine = new SimplePanel();

    SimplePanel recentAddedSkills = new SimplePanel();

    private Presenter presenter;

    public final Login login;

    @Inject
    public HomeView(EventBus eventBus,
                    SkillAppealTimeLineActivity skillAppealTimeLineActivity,
                    RecentAddedSkillsActivity recentAddedSkillsActivity,
                    TimeLineActivity timeLineActivity,
                    Login login) {
        this.login = login;
        initWidget(uiBinder.createAndBindUi(this));

        menuBar.addTab("Skill Commnet");
        menuBar.addTab("Appeal");
        menuBar.addTab("New Skils");
        menuBar.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                contentsPanel.clear();
                switch (event.getSelectedItem()) {
                case 0:
                    contentsPanel.setWidget(timeLine);
                    break;
                case 1:
                    contentsPanel.setWidget(skillAppealTimeLine);
                    break;
                case 2:
                    contentsPanel.setWidget(recentAddedSkills);
                    break;
                }
            }
        });
        menuBar.selectTab(0, false);
        contentsPanel.add(timeLine);

        timeLineActivity.start(timeLine, eventBus);
        skillAppealTimeLineActivity.start(skillAppealTimeLine, eventBus);
        recentAddedSkillsActivity.start(recentAddedSkills, eventBus);
    }

    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }
}
