package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.HomeDisplay;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class HomeActivity extends SkillMapActivity implements
        HomeDisplay.Presenter {

    private final Provider<HomeDisplay> displayProvider;
    private HomeDisplay display;

    @Inject
    public HomeActivity(Provider<HomeDisplay> displayProvider) {
        this.displayProvider = displayProvider;
    }

    @Override
    public void start(final AcceptsOneWidget panel,final EventBus eventBus) {
        panel.setWidget(new Image(Resources.INSTANCE.loader()));
        setDisplay(displayProvider.get());
        display.setPresenter(this);
        panel.setWidget(display);
    }

    @Override
    public void setDisplay(HomeDisplay display) {
        this.display = display;
    }
}
