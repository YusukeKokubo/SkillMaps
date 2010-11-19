package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.RecentEntriedUsersDisplay;
import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RecentEntriedUsers extends Composite implements RecentEntriedUsersDisplay{

    private static RecentEntriedUsersUiBinder uiBinder = GWT
        .create(RecentEntriedUsersUiBinder.class);

    AccountServiceAsync service = GWT.create(AccountService.class);

    interface RecentEntriedUsersUiBinder extends
            UiBinder<Widget, RecentEntriedUsers> {
    }

    @UiField
    VerticalPanel users;

    @UiField
    UserDialog userDialog;

    private Presenter presenter;

    @Inject
    public RecentEntriedUsers(final Login login) {
        initWidget(uiBinder.createAndBindUi(this));

        service.getRecentEntriedUsers(new AsyncCallback<Profile[]>() {
            @Override
            public void onSuccess(Profile[] result) {
                for (Profile user : result) {
                    users.add(new UserThumnail(login, user, userDialog));
                }
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }

}
