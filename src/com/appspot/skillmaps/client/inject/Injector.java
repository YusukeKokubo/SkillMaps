package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.client.place.HomePlace;
import com.appspot.skillmaps.client.place.MyPagePlace;
import com.appspot.skillmaps.client.place.SkillListPlace;
import com.appspot.skillmaps.client.place.SkillOwnersPlace;
import com.appspot.skillmaps.client.place.UserListPlace;
import com.appspot.skillmaps.client.place.UserPlace;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.client.ui.ContentsPanel;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@GinModules(SkillMapsModule.class)
public interface Injector extends Ginjector {

    PlaceController getPlaceController();

    PlaceHistoryHandler getHistoryHandler();

    AccountServiceAsync getAccountService();

    ActivityManager getActivityManager();

    Login getLogin();

    @Singleton ContentsPanel getContentsPanel();

    @Named("contents") SimplePanel getContentPanel();

    @Named("dashboard") SimplePanel getDashboard();

    HomePlace.Tokenizer getHomePlaceTokenizer();

    MyPagePlace.Tokenizer getMyPagePlaceTokenizer();

    SkillListPlace.Tokenizer getSkillListPlaceTokenizer();

    UserListPlace.Tokenizer getUserListPlaceTokenizer();

    SkillOwnersPlace.Tokenizer getSkillOwnersPlaceTokenizer();

    UserPlace.Tokenizer getUserPlaceTokenizer();

}
