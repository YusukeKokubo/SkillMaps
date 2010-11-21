package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.RecentEntriedUsersDisplay;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class RecentEntriedUsersActivity extends AbstractActivity implements RecentEntriedUsersDisplay.Presenter {

    private final Provider<RecentEntriedUsersDisplay> displayProvider;
    private final Provider<AccountServiceAsync> serviceProvider;

    @Inject
    public RecentEntriedUsersActivity(Provider<RecentEntriedUsersDisplay> displayProvider,
                                        Provider<AccountServiceAsync> serviceProvider) {
        this.displayProvider = displayProvider;
        this.serviceProvider = serviceProvider;
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
                // TODO 自動生成されたメソッド・スタブ

            }
        });
    }

    @Override
    public void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        final RecentEntriedUsersDisplay display = displayProvider.get();
        display.setPresenter(this);
        panel.setWidget(display);
        serviceProvider.get().getRecentEntriedUsers(new AsyncCallback<Profile[]>() {

            @Override
            public void onSuccess(Profile[] result) {
                display.setRecentEntriedUsers(result);
            }

            @Override
            public void onFailure(Throwable caught) {

            }
        });
    }

}

