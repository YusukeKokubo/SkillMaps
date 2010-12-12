package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface MyPageDisplay extends IsWidget{
    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter<MyPageDisplay>{

        void registProfile();

    }

}
