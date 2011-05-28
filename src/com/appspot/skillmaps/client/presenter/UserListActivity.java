package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.UserListDisplay;
import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserListActivity extends SkillMapActivity implements UserListDisplay.Presenter {

    AccountServiceAsync service = GWT.create(AccountService.class);
    private UserListDisplay display;
    private final Provider<UserListDisplay> displayProvider;

    @Inject
    public UserListActivity(Provider<UserListDisplay> displayProvider) {
        this.displayProvider = displayProvider;
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

    @Override
    public void loadNextUsers(UserListResultDto userListResultDto) {
        loadUsers(userListResultDto);
    }

    private void loadUsers(UserListResultDto userListResultDto) {
        service.getUsers(userListResultDto.getEncodedCursor(),
            userListResultDto.getEncodedFilter(),
            userListResultDto.getEncodedSorts(),
            new AsyncCallback<UserListResultDto>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(UserListResultDto result) {
                display.setUserList(result);
            }
        });
    }

    private void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        if(display == null){
            setDisplay(displayProvider.get());
            display.setPresenter(this);
        }

        display.clearData();

        loadUsers(new UserListResultDto());
        panel.setWidget(display);
    }

    @Override
    public void setDisplay(UserListDisplay display) {
        this.display = display;
    }

    @Override
    public void findUsers(String id) {
        service.findUsers(id, new AsyncCallback<Profile[]>() {

            @Override
            public void onSuccess(Profile[] result) {
                display.setUserList(result);
            }

            @Override
            public void onFailure(Throwable caught) {

            }
        });
    }
}
