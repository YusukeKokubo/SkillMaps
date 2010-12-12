package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.ui.IsWidget;

public interface RecentEntriedUsersDisplay extends IsWidget {

    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter<RecentEntriedUsersDisplay>{

    }

    void setRecentEntriedUsers(Profile[] profiles);

}
