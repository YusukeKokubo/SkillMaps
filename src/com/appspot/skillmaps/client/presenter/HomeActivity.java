package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.HomeDisplay;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class HomeActivity extends AbstractActivity implements
        HomeDisplay.Presenter {

    private final Provider<HomeDisplay> displayProvider;

    @Inject
    public HomeActivity(Provider<HomeDisplay> displayProvider) {
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

    @Override
    public void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        HomeDisplay homeDisplay = displayProvider.get();
        homeDisplay.setPresenter(this);
        panel.setWidget(homeDisplay);
    }

}
