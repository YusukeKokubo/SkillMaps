package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.client.place.InjectablePlaceController;
import com.appspot.skillmaps.client.place.InjectablePlaceHistoryHandler;
import com.appspot.skillmaps.client.presenter.AppActivityMapper;
import com.appspot.skillmaps.client.presenter.InjectableActivityManager;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Singleton;

public class SkillMapsModule extends AbstractGinModule {

    @Override
    protected void configure() {
        //global parameter
        bind(Login.class).asEagerSingleton();

        install(new DisplayModule());

        //MVP modules
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(ActivityMapper.class).to(AppActivityMapper.class).asEagerSingleton();
        bind(PlaceController.class).to(InjectablePlaceController.class).in(Singleton.class);
        bind(ActivityManager.class).to(InjectableActivityManager.class).in(Singleton.class);
        bind(PlaceHistoryHandler.class).to(InjectablePlaceHistoryHandler.class).in(Singleton.class);

    }

}
