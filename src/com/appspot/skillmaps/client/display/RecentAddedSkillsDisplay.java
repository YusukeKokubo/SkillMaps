package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.gwt.user.client.ui.IsWidget;

public interface RecentAddedSkillsDisplay extends IsWidget {

    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter<RecentAddedSkillsDisplay>{

    }

    void setRecentAddedSkills(SkillAssertion[] result);
}
