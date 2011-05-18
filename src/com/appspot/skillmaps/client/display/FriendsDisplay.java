package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public interface FriendsDisplay extends IsWidget {
    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter<FriendsDisplay>{
    }

    void reloadUsersPanel(Profile[] users);

    HasWidgets getFriendsPanel();
}
