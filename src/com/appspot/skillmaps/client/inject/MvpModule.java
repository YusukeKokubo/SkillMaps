package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.client.place.AppPlaceHistoryMapper;
import com.appspot.skillmaps.client.place.HomePlace;
import com.appspot.skillmaps.client.presenter.AppActivityMapper;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class MvpModule extends AbstractGinModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public EventBus eventBus(){
        return new SimpleEventBus();
    }

    @Provides
    @Singleton
    public PlaceController placeController(EventBus eventBus) {
        return new PlaceController(eventBus);
    }

    @Provides
    @Singleton
    public ActivityMapper activityMapper(){
        return new AppActivityMapper();
    }

    @Provides
    @Singleton
    public ActivityManager activityManager(ActivityMapper mapper,EventBus eventBus){
        ActivityManager activityManager = new ActivityManager(mapper, eventBus);
        SimplePanel contentsPanel = new SimplePanel();
        activityManager.setDisplay(contentsPanel);
        RootPanel.get("contents").add(contentsPanel);
        return activityManager;
    }

    @Provides
    @Singleton
    public PlaceHistoryHandler placeHistoryHandler(
            AppPlaceHistoryMapper mapper, PlaceController placeController,
            EventBus eventBus, HomePlace defaultPlace, Injector injector) {

        mapper.setFactory(injector);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(mapper);
        historyHandler.register(placeController, eventBus, defaultPlace);
        return historyHandler;
    }
}
