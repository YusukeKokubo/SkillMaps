package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.client.place.HomePlace;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

@GinModules(SkillMapsModule.class)
public interface Injector extends Ginjector {

    PlaceController getPlaceController();

    PlaceHistoryHandler getHistoryHandler();

    AccountServiceAsync getAccountService();

    ActivityManager getActivityManager();

    Login getLogin();

    HomePlace.Tokenizer getHomePlaceTokenizer();

}
