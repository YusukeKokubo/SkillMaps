package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.HomeDisplay;
import com.appspot.skillmaps.client.presenter.RecentAddedSkillsActivity;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class HomeView extends Composite implements HomeDisplay {

    private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

    interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
    }

    @UiField
    SimplePanel contentsPanel;

    @UiField
    AcceptsOneWidget homeHeaderPanel;

    SimplePanel recentAddedSkills = new SimplePanel();

    private Presenter presenter;

    public final Login login;

    @Inject
    public HomeView(EventBus eventBus,
                    RecentAddedSkillsActivity recentAddedSkillsActivity,
                    Login login) {
        this.login = login;
        initWidget(uiBinder.createAndBindUi(this));
        contentsPanel.setWidget(recentAddedSkills);
        recentAddedSkillsActivity.start(recentAddedSkills, eventBus);
    }

    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }
}
