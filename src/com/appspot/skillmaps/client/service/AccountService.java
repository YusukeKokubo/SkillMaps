package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface AccountService extends RemoteService {

    Login login(String requestUri);

    void putProfile(Profile act);

    Profile[] getUsers();

    Profile getUser(String id);

    UserListResultDto getUsers(String encodedCursor,
                                String encodedFilter,
                                String encodedSorts);

    UserListResultDto getUserList();

    Profile[] getRecentEntriedUsers();

    String getSignUrl(String backUrl);

    Profile[] getFriends(Profile p);

    Profile[] getFollowingBy(Profile p);

    Profile[] getFollowerTo(Profile p);

    Profile[] getFriends();

    Profile[] getRecentEntriedUsersWithCursor(int pageNum);

    Profile[] findUsers(String id);

    Profile[] getFollowerTo();

    Profile[] getFollowingBy();

    Profile[] getUsers(SkillAssertion assertion);
}
