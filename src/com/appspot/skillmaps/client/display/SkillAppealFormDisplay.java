package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface SkillAppealFormDisplay extends IsWidget{

    void setPresenter(Presenter presenter);

    void initPresenter();

    public static interface Presenter extends DisplayPresenter<SkillAppealFormDisplay>{

        void registSkillAppeal();

        void setupDisplay(SkillAppealFormDisplay display);

    }

    HasValue<Boolean> getSendTwitter();

}
