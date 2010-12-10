package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public interface UserUIDisplay extends IsWidget {

    void setPresenter(Presenter presenter);

    public static interface Presenter extends DisplayPresenter{

        void showSkillAddDialog();

        void reloadSkills();

        void showSkillOwnersPopup(Skill skill);

        void getSkillRelations(Skill skill,
                AsyncCallback<SkillRelation[]> asyncCallback);

        void showAgreedDialog(Skill skill);

        void setDisplay(UserUIDisplay display);

        void setProfile(Profile profile);

        void gotoUser(String id);
    }

    void setProfile(Profile profile);

    void reloadSkills(Skill[] skills);

    SimplePanel getSkillsPanel();
}