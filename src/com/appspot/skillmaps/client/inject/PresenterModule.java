package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.client.display.ActivateGuidanceDisplay;
import com.appspot.skillmaps.client.display.FriendsDisplay;
import com.appspot.skillmaps.client.display.HomeDisplay;
import com.appspot.skillmaps.client.display.MyPageDisplay;
import com.appspot.skillmaps.client.display.RecentAddedSkillsDisplay;
import com.appspot.skillmaps.client.display.RecentEntriedUsersDisplay;
import com.appspot.skillmaps.client.display.SigninGuidanceDisplay;
import com.appspot.skillmaps.client.display.SkillAppealTimeLineDisplay;
import com.appspot.skillmaps.client.display.SkillListDisplay;
import com.appspot.skillmaps.client.display.SkillOwnersDisplay;
import com.appspot.skillmaps.client.display.UserListDisplay;
import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.presenter.ActivateGuidanceActivity;
import com.appspot.skillmaps.client.presenter.FriendsActivity;
import com.appspot.skillmaps.client.presenter.HomeActivity;
import com.appspot.skillmaps.client.presenter.MyPageActivity;
import com.appspot.skillmaps.client.presenter.RecentAddedSkillsActivity;
import com.appspot.skillmaps.client.presenter.RecentEntriedUsersActivity;
import com.appspot.skillmaps.client.presenter.SigninGuidanceActivity;
import com.appspot.skillmaps.client.presenter.SkillAppealTimeLineActivity;
import com.appspot.skillmaps.client.presenter.SkillListActivity;
import com.appspot.skillmaps.client.presenter.SkillOwnersActivity;
import com.appspot.skillmaps.client.presenter.UserListActivity;
import com.appspot.skillmaps.client.presenter.UserUIActivity;
import com.google.gwt.inject.client.AbstractGinModule;

public class PresenterModule extends AbstractGinModule {

    @Override
    protected void configure() {
        //bind presenter modules
        bind(HomeDisplay.Presenter.class).to(HomeActivity.class);
        bind(MyPageDisplay.Presenter.class).to(MyPageActivity.class);
        bind(SkillListDisplay.Presenter.class).to(SkillListActivity.class);
        bind(SkillOwnersDisplay.Presenter.class).to(SkillOwnersActivity.class);
        bind(UserListDisplay.Presenter.class).to(UserListActivity.class);
        bind(UserUIDisplay.Presenter.class).to(UserUIActivity.class);
        bind(FriendsDisplay.Presenter.class).to(FriendsActivity.class);

        bind(ActivateGuidanceDisplay.Presenter.class).to(ActivateGuidanceActivity.class);
        bind(RecentAddedSkillsDisplay.Presenter.class).to(RecentAddedSkillsActivity.class);
        bind(RecentEntriedUsersDisplay.Presenter.class).to(RecentEntriedUsersActivity.class);
        bind(SkillAppealTimeLineDisplay.Presenter.class).to(SkillAppealTimeLineActivity.class);
        bind(SigninGuidanceDisplay.Presenter.class).to(SigninGuidanceActivity.class);
    }
}
