package com.appspot.skillmaps.client.presenter;

import java.util.Arrays;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.MyPageDisplay;
import com.appspot.skillmaps.client.place.MyPagePlace;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.client.ui.ProfileUI;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MyPageActivity extends SkillMapActivity implements
        MyPageDisplay.Presenter {

    interface Driver extends SimpleBeanEditorDriver<Profile, ProfileUI>{}

    private Driver driver = GWT.create(Driver.class);

    private final Login login;
    private final Provider<SigninGuidanceActivity> signinGuidanceProvider;
    private final Provider<MyPageDisplay> displayProvier;

    private final Provider<AccountServiceAsync> serviceProvider;

    private final Provider<PlaceController> placeControllerProvier;

    private final Provider<MyPagePlace> pageProvier;

    private AcceptsOneWidget panel;

    @Inject
    public MyPageActivity(Login login,
            Provider<MyPageDisplay> displayProvier,
            Provider<SigninGuidanceActivity> signinGuidanceProvider,
            Provider<AccountServiceAsync> serviceProvider,
            Provider<PlaceController> placeControllerProvier,
            Provider<MyPagePlace> pageProvier) {
        this.login = login;
        this.displayProvier = displayProvier;
        this.signinGuidanceProvider = signinGuidanceProvider;
        this.serviceProvider = serviceProvider;
        this.placeControllerProvier = placeControllerProvier;
        this.pageProvier = pageProvier;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
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

    @Override
    public void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        this.panel = panel;
        if (login.isLoggedIn()) {
            MyPageDisplay display = displayProvier.get();

            display.setPresenter(this);

            driver.initialize((ProfileUI)display);

            driver.edit(login.getProfile());

            panel.setWidget(display);
        } else {
            signinGuidanceProvider.get().initDisplay(panel, eventBus);
        }

    }

    @Override
    public void registProfile() {
        Profile p = driver.flush();
        panel.setWidget(new Image(Resources.INSTANCE.loader()));
        serviceProvider.get().putProfile(p, new AsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                Window.alert("更新しました!!");
                placeControllerProvier.get().goTo(pageProvier.get());

            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage() + "\n" + Arrays.toString(caught.getStackTrace()));
            }
        });

    }


}
