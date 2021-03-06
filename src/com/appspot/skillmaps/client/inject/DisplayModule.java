package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.client.display.ActivateGuidanceDisplay;
import com.appspot.skillmaps.client.display.FriendsDisplay;
import com.appspot.skillmaps.client.display.HomeDisplay;
import com.appspot.skillmaps.client.display.MyPageDisplay;
import com.appspot.skillmaps.client.display.RecentAddedSkillsDisplay;
import com.appspot.skillmaps.client.display.RecentEntriedUsersDisplay;
import com.appspot.skillmaps.client.display.SigninGuidanceDisplay;
import com.appspot.skillmaps.client.display.SkillAssertionUIDisplay;
import com.appspot.skillmaps.client.display.SkillListDisplay;
import com.appspot.skillmaps.client.display.SkillOwnersDisplay;
import com.appspot.skillmaps.client.display.UserListDisplay;
import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.ui.ActivateGuidance;
import com.appspot.skillmaps.client.ui.Friends;
import com.appspot.skillmaps.client.ui.HomeView;
import com.appspot.skillmaps.client.ui.ProfileUI;
import com.appspot.skillmaps.client.ui.RecentAddedSkills;
import com.appspot.skillmaps.client.ui.RecentEntriedUsers;
import com.appspot.skillmaps.client.ui.SigninGuidance;
import com.appspot.skillmaps.client.ui.SkillAssertionUI;
import com.appspot.skillmaps.client.ui.SkillListUI;
import com.appspot.skillmaps.client.ui.SkillOwners;
import com.appspot.skillmaps.client.ui.UserListUI;
import com.appspot.skillmaps.client.ui.UserUI;
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
        bind(UserUIDisplay.class).to(UserUI.class);
        bind(FriendsDisplay.class).to(Friends.class);
        bind(SkillAssertionUIDisplay.class).to(SkillAssertionUI.class);

        bind(ActivateGuidanceDisplay.class).to(ActivateGuidance.class);
        bind(RecentAddedSkillsDisplay.class).to(RecentAddedSkills.class);
        bind(RecentEntriedUsersDisplay.class).to(RecentEntriedUsers.class);
        bind(SigninGuidanceDisplay.class).to(SigninGuidance.class);
    }
}
