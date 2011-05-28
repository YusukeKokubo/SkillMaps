package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public interface UserListDisplay extends IsWidget {
    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter<UserListDisplay>{
        void loadNextUsers(UserListResultDto userListResultDto);

        void findUsers(String text);
    }

    void setUserList(UserListResultDto userListResultDto);
    HasWidgets getUserListPanel();
    void clearData();
    void setUserList(Profile[] users);
}
