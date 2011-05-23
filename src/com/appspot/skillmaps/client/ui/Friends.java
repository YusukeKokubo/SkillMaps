package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.FriendsDisplay;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class Friends extends Composite implements FriendsDisplay {

    private static UsersUiBinder uiBinder = GWT.create(UsersUiBinder.class);

    interface UsersUiBinder extends UiBinder<Widget, Friends> {
    }

    @UiField
    VerticalPanel usersPanel;

    @UiField
    SimplePanel contentsPanel;

    @UiField
    DecoratedTabBar menuBar;

    private final Provider<UserThumnail> utProvier;

    private Presenter presenter;

//    private final Injector injector;

    @Inject
    public Friends(Login login,Provider<UserThumnail> utProvier , Injector injector) {
        this.utProvier = utProvier;
//        this.injector = injector;
        initWidget(uiBinder.createAndBindUi(this));
        if(login != null && login.isLoggedIn()) {
            menuBar.addTab("Friends");
            menuBar.addTab("Follow");
            menuBar.addTab("Follower");
            menuBar.selectTab(0, false);
            usersPanel.add(new Image(Resources.INSTANCE.loader()));

        } else {
            usersPanel.add(new Label("この機能を利用するにはログインしてください。"));
        }
    }

    @Override
    public void reloadUsersPanel(Profile[] users) {
        usersPanel.clear();

        if(users == null || users.length == 0) {

            usersPanel.add(new Label("ユーザがいません。 他のユーザにSkillなどを追加してみましょう!"));

            return;
        }

        for (final Profile user : users) {
            FocusPanel panel = new FocusPanel();
            UserThumnail userThumnail = utProvier.get();
            userThumnail.setUser(user);
            panel.add(userThumnail);
            usersPanel.add(panel);
        }
    }

    @UiHandler("menuBar")
    public void onSelectionMenuBar(SelectionEvent<Integer> event) {

        usersPanel.clear();
        usersPanel.add(new Image(Resources.INSTANCE.loader()));

        switch (event.getSelectedItem()) {
        case 0:
            presenter.reloadFriends();
            break;
        case 1:
            presenter.reloadFollowing();
            break;
        case 2:
            presenter.reloadFollowerTo();
            break;
        default:
            break;
        }

    }

    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }

    @Override
    public HasWidgets getFriendsPanel() {
        return usersPanel;
    }
}
