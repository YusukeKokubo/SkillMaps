package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public interface UserListDisplay extends IsWidget {
    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter<UserListDisplay>{
        void loadNextUsers(int pageIndex, UserListResultDto userListResultDto);
        void loadPrevUsers(int pageIndex, UserListResultDto userListResultDto);
    }

    void setUserList(int pn, UserListResultDto userListResultDto);
    HasWidgets getUserListPanel();
}
