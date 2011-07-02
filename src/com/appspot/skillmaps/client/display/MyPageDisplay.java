package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.ui.IsWidget;

public interface MyPageDisplay extends IsWidget {
    void setPresenter(Presenter presenter);

    void setProfile(Profile profile);

    Profile getProfile();

    public static interface Presenter extends DisplayPresenter<MyPageDisplay> {
        void registProfile();
    }
}
