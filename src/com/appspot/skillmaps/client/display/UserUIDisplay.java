package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public interface UserUIDisplay extends IsWidget {

    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter<UserUIDisplay>{

        void showSkillAddDialog();
        void reloadSkills();
        void showSkillOwnersPopup(Skill skill);
        void getSkillRelations(Skill skill,
                AsyncCallback<SkillRelation[]> asyncCallback);
        void setDisplay(UserUIDisplay display);
        void setProfile(Profile profile);
        void gotoUser(String id);
        void getAssertions(SkillA skill, VerticalPanel assertions);
        void addAssertion(SkillAssertion assertion);
    }

    void setProfile(Profile profile);
    void reloadSkills(SkillA[] skills);
    SimplePanel getSkillsPanel();
}
