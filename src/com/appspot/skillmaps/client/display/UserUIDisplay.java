package com.appspot.skillmaps.client.display;

import com.appspot.skillmaps.client.presenter.DisplayPresenter;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasWidgets;
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
        void getSkillComments(Key key, HasWidgets commentPanel);
        void showSkillCommentForm(Key key, VerticalPanel commentsPanel);
        void showAgreedDialog(Anchor agreedForm, Skill skill, SkillRelation rel);
    }

    void setProfile(Profile profile);
    void reloadSkills(SkillA[] skills);
    SimplePanel getSkillsPanel();
}
