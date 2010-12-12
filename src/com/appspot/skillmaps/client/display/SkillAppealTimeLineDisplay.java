package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.user.client.ui.IsWidget;

public interface SkillAppealTimeLineDisplay extends IsWidget {

    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter<SkillAppealTimeLineDisplay>{

    }

    void setSkillAppeals(SkillAppeal[] as);

}
