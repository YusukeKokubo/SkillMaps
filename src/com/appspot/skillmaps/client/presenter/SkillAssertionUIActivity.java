
package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.SkillAssertionUIDisplay;
import com.appspot.skillmaps.client.place.SkillAssertionPlace;
import com.appspot.skillmaps.client.place.UserPlace;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.SkillAddDialog;
import com.appspot.skillmaps.client.ui.UserThumnail;
import com.appspot.skillmaps.client.ui.parts.PartsFactory;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class SkillAssertionUIActivity extends SkillMapActivity implements SkillAssertionUIDisplay.Presenter {

    private SkillAssertion assertion;

    private EventBus eventBus;

    @Inject
    private PlaceController placeController;

    @Inject
    private Provider<SkillAddDialog> skillAddDialogProvider;
    @Inject
    private Provider<SkillAssertionUIDisplay> displayProvider;
    @Inject
    private Provider<SkillServiceAsync> serviceProvider;
    @Inject
    private Provider<UserThumnail> utProvider;
    @Inject
    @Named("skillOwnersPermalink")
    private Provider<Anchor> permalinkProvider;
    @Inject
    private Provider<AccountServiceAsync> accountServiceProvider;
    @Inject
    private Provider<UserPlace> placeProvider;
    @Inject
    private PartsFactory partsFactory;

    private SkillAssertionUIDisplay display;

    private HandlerRegistration hr;

    @Inject
    Login login;

    @Override
    public void setAssertion(SkillAssertion assertion){
        this.assertion = assertion;
    }

    private void initDisplay(final AcceptsOneWidget panel, EventBus eventBus) {
        this.eventBus = eventBus;
        panel.setWidget(new Image(Resources.INSTANCE.loader()));
        serviceProvider.get().getAssertion(((SkillAssertionPlace)place).getAssertionKey(), new AsyncCallback<SkillAssertion>() {
            @Override
            public void onSuccess(SkillAssertion result) {
                setAssertion(result);
                display = displayProvider.get();
                display.setAssertion(result);
                panel.setWidget(display);
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

    @Override
    public void setDisplay(SkillAssertionUIDisplay display){
        this.display = display;
    }

    @Override
    public void start(final AcceptsOneWidget panel,final EventBus eventBus) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override
            public void onSuccess() {
                initDisplay(panel, eventBus);
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
    }

    @Override
    public SkillAssertion getAssertion(SkillAssertion assertion) {
        return this.assertion;
    }
}
