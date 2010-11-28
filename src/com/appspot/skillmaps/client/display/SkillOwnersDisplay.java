package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.user.client.ui.IsWidget;

public interface SkillOwnersDisplay extends IsWidget {
    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter{

        void setSkillName(String skillName);

    }

    void setSkills(Skill[] result);

}
