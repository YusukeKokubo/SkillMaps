package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.client.display.ActivateGuidanceDisplay;
import com.appspot.skillmaps.client.display.HomeDisplay;
import com.appspot.skillmaps.client.display.MyPageDisplay;
import com.appspot.skillmaps.client.display.RecentAddedSkillsDisplay;
import com.appspot.skillmaps.client.display.RecentEntriedUsersDisplay;
import com.appspot.skillmaps.client.display.SigninGuidanceDisplay;
import com.appspot.skillmaps.client.display.SkillAppealFormDisplay;
import com.appspot.skillmaps.client.display.SkillAppealTimeLineDisplay;
import com.appspot.skillmaps.client.display.SkillListDisplay;
import com.appspot.skillmaps.client.display.SkillOwnersDisplay;
import com.appspot.skillmaps.client.display.UserListDisplay;
import com.appspot.skillmaps.client.ui.ActivateGuidance;
import com.appspot.skillmaps.client.ui.HomeView;
import com.appspot.skillmaps.client.ui.ProfileUI;
import com.appspot.skillmaps.client.ui.RecentAddedSkills;
import com.appspot.skillmaps.client.ui.RecentEntriedUsers;
import com.appspot.skillmaps.client.ui.SigninGuidance;
import com.appspot.skillmaps.client.ui.SkillAppealForm;
import com.appspot.skillmaps.client.ui.SkillAppealTimeLine;
import com.appspot.skillmaps.client.ui.SkillListUI;
import com.appspot.skillmaps.client.ui.SkillOwners;
import com.appspot.skillmaps.client.ui.UserListUI;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class DisplayModule extends AbstractGinModule {

    @Override
    protected void configure() {

        //bind display modules
        bind(HomeDisplay.class).to(HomeView.class);
        bind(MyPageDisplay.class).to(ProfileUI.class);
        bind(SkillListDisplay.class).to(SkillListUI.class);
        bind(SkillOwnersDisplay.class).to(SkillOwners.class);
        bind(UserListDisplay.class).to(UserListUI.class).in(Singleton.class);
        bind(ActivateGuidanceDisplay.class).to(ActivateGuidance.class);
        bind(RecentAddedSkillsDisplay.class).to(RecentAddedSkills.class);
        bind(RecentEntriedUsersDisplay.class).to(RecentEntriedUsers.class);
        bind(SkillAppealFormDisplay.class).to(SkillAppealForm.class);
        bind(SkillAppealTimeLineDisplay.class).to(SkillAppealTimeLine.class);
        bind(SigninGuidanceDisplay.class).to(SigninGuidance.class);
    }

}
