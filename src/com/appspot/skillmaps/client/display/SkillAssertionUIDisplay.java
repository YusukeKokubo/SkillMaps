package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.gwt.user.client.ui.IsWidget;

public interface SkillAssertionUIDisplay extends IsWidget {

    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter<SkillAssertionUIDisplay>{
        SkillAssertion getAssertion(SkillAssertion assertion);

        void setAssertion(SkillAssertion assertion);
    }

    void setAssertion(SkillAssertion assertion);
}
