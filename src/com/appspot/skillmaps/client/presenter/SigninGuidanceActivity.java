package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.SigninGuidanceDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SigninGuidanceActivity extends SkillMapActivity implements SigninGuidanceDisplay.Presenter{

    private final Provider<SigninGuidanceDisplay> displayProvider;
    private SigninGuidanceDisplay display;

    @Inject
    public SigninGuidanceActivity(Provider<SigninGuidanceDisplay> displayProvider) {
        this.displayProvider = displayProvider;
    }

    @Override
    public void start(final AcceptsOneWidget panel,final EventBus eventBus) {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {
                initDisplay(panel, eventBus);

            }

            @Override
            public void onFailure(Throwable reason) {
            }
        });

    }

    private void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        setDisplay(displayProvider.get());
        panel.setWidget(display);
    }

    @Override
    public void setDisplay(SigninGuidanceDisplay display) {
        this.display = display;

    }

}
