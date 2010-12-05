package com.appspot.skillmaps.client.presenter;

import java.util.HashMap;
import java.util.Map;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.UserListDisplay;
import com.appspot.skillmaps.client.place.UserListPlace;
import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserListActivity extends SkillMapActivity implements UserListDisplay.Presenter {

    AccountServiceAsync service = GWT.create(AccountService.class);

    private static final Map<Integer , UserListResultDto> pageMap = new HashMap<Integer, UserListResultDto>();

    private UserListDisplay display;

    private final Provider<UserListDisplay> displayProvider;

    private final PlaceController placeController;

    private final Provider<UserListPlace> placeProvider;

    @Inject
    public UserListActivity(Provider<UserListDisplay> displayProvider,
                            PlaceController placeController,
                            Provider<UserListPlace> placeProvider) {
        this.displayProvider = displayProvider;
        this.placeController = placeController;
        this.placeProvider = placeProvider;

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
    public void loadNextUsers(int pageIndex ,UserListResultDto userListResultDto) {

        loadUsers(pageIndex ,0, userListResultDto , true);
    }

    private void loadUsers(final int pageIndex,
                            int pn,
                            UserListResultDto userListResultDto,
                            final boolean usePlace) {
        if(!pageMap.containsKey(pageIndex)){
            service.getUsers(pn,
                        userListResultDto.getEncodedCursor(),
                        userListResultDto.getEncodedFilter(),
                        userListResultDto.getEncodedSorts(),
                new AsyncCallback<UserListResultDto>() {

                    @Override
                    public void onFailure(Throwable caught) {
                    }

                    @Override
                    public void onSuccess(UserListResultDto result) {
                        pageMap.put(pageIndex, result);
                        if(!usePlace){
                            display.setUserList(pageIndex, result);
                        }else{
                            placeController.goTo(placeProvider.get().page(pageIndex));
                        }
                    }
            });

        } else {
            if(!usePlace){
                display.setUserList(pageIndex, pageMap.get(pageIndex));
            }else{
                placeController.goTo(placeProvider.get().page(pageIndex));
            }
        }
    }

    @Override
    public void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        if(display == null){
            display = displayProvider.get();
            display.setPresenter(this);
        }

        UserListPlace userListPlace = (UserListPlace)place;
        int pageIndex = userListPlace.getPageNumber();
        if(pageMap.containsKey(pageIndex)){
            display.setUserList(pageIndex, pageMap.get(pageIndex));
        } else {
            loadUsers(pageIndex, pageIndex, new UserListResultDto() , false);
        }
        panel.setWidget(display);
    }

    @Override
    public void loadPrevUsers(int pageIndex, UserListResultDto userListResultDto) {
        loadUsers(pageIndex ,-1 * pageIndex, userListResultDto , true);
    }

}
