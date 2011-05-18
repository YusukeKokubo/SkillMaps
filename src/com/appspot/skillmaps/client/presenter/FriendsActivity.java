package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.FriendsDisplay;
import com.appspot.skillmaps.client.place.FriendsPlace;
import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class FriendsActivity extends SkillMapActivity implements FriendsDisplay.Presenter {

    AccountServiceAsync service = GWT.create(AccountService.class);
    private FriendsDisplay display;
    private final Provider<FriendsDisplay> displayProvider;
//    private final PlaceController placeController;
//    private final Provider<FriendsPlace> placeProvider;

    @Inject
    public FriendsActivity(Provider<FriendsDisplay> displayProvider,
                            PlaceController placeController,
                            Provider<FriendsPlace> placeProvider) {
        this.displayProvider = displayProvider;
//        this.placeController = placeController;
//        this.placeProvider = placeProvider;
    }

    @Override
    public void start(final AcceptsOneWidget panel,final EventBus eventBus) {
        panel.setWidget(new Image(Resources.INSTANCE.loader()));
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
        if(display == null){
            setDisplay(displayProvider.get());
            display.setPresenter(this);
        }

        service.getFriends(new AsyncCallback<Profile[]>() {
            @Override
            public void onSuccess(Profile[] result) {
                display.reloadUsersPanel(result);
            }
            
            @Override
            public void onFailure(Throwable caught) {
            }
        });
        panel.setWidget(display);
    }

    @Override
    public void setDisplay(FriendsDisplay display) {
        this.display = display;
    }
}
