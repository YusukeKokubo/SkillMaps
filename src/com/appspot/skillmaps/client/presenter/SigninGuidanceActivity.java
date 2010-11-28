package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.SigninGuidanceDisplay;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SigninGuidanceActivity extends SkillMapActivity implements SigninGuidanceDisplay.Presenter{

    private final Provider<SigninGuidanceDisplay> displayProvider;

    @Inject
    public SigninGuidanceActivity(Provider<SigninGuidanceDisplay> displayProvider) {
        this.displayProvider = displayProvider;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        initDisplay(panel, eventBus);

    }

    @Override
    public void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(displayProvider.get());
    }

}
