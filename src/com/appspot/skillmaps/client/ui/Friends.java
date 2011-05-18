package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.FriendsDisplay;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class Friends extends Composite implements FriendsDisplay{

    private static UsersUiBinder uiBinder = GWT.create(UsersUiBinder.class);

    interface UsersUiBinder extends UiBinder<Widget, Friends> {
    }

    @UiField
    VerticalPanel usersPanel;

    private final Provider<UserThumnail> utProvier;

    private Presenter presenter;

//    private final Injector injector;

    @Inject
    public Friends(Provider<UserThumnail> utProvier , Injector injector) {
        this.utProvier = utProvier;
//        this.injector = injector;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void reloadUsersPanel(Profile[] users) {
        usersPanel.clear();
        for (final Profile user : users) {
            FocusPanel panel = new FocusPanel();
            UserThumnail userThumnail = utProvier.get();
            userThumnail.setUser(user);
            panel.add(userThumnail);
            usersPanel.add(panel);
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
