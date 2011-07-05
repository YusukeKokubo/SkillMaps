package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.ActivateGuidanceDisplay;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ActivateGuidanceActivity extends SkillMapActivity implements
        ActivateGuidanceDisplay.Presenter {

    private final Provider<ActivateGuidanceDisplay> displayProvider;
    private ActivateGuidanceDisplay display;

    @Inject
    public ActivateGuidanceActivity(
            Provider<ActivateGuidanceDisplay> displayProvider) {
        this.displayProvider = displayProvider;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        setDisplay(displayProvider.get());
        display.setPresenter(this);
        panel.setWidget(display);
    }

    @Override
    public void setDisplay(ActivateGuidanceDisplay display) {
        this.display = display;
    }
}
