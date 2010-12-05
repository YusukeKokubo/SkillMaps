package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.user.client.ui.IsWidget;

public interface UserUIDisplay extends IsWidget {

    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter{

        void showSkillAddDialog();

        void reloadSkills();
    }

    void setProfile(Profile profile);

    void reloadSkills(Skill[] skills);
}
