package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.google.gwt.user.client.ui.IsWidget;

public interface SkillListDisplay extends IsWidget {
    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter{

    }

    void setSkillMaps(SkillMap[] skillMaps);

}
