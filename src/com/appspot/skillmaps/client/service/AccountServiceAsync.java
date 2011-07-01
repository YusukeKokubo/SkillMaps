package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AccountServiceAsync {

    void login(String requestUri, AsyncCallback<Login> callback);

    void putProfile(Profile act, AsyncCallback<Void> callback);

    void getUsers(AsyncCallback<Profile[]> callback);

    void getUser(String id, AsyncCallback<Profile> callback);

    void getUsers(String encodedCursor, String encodedFilter,
            String encodedSorts, AsyncCallback<UserListResultDto> callback);

    void getUserList(AsyncCallback<UserListResultDto> callback);

    void getRecentEntriedUsers(AsyncCallback<Profile[]> callback);

    void getSignUrl(String backUrl, AsyncCallback<String> callback);

    void getFriends(Profile p, AsyncCallback<Profile[]> callback);

    void getFollowingBy(Profile p, AsyncCallback<Profile[]> callback);

    void getFollowerTo(Profile p, AsyncCallback<Profile[]> callback);

    void getFriends(AsyncCallback<Profile[]> callback);

    void getRecentEntriedUsersWithCursor(int pageNum,
            AsyncCallback<Profile[]> callback);

    void findUsers(String id, AsyncCallback<Profile[]> callback);

    void getFollowerTo(AsyncCallback<Profile[]> callback);

    void getFollowingBy(AsyncCallback<Profile[]> callback);

    void getUsers(SkillAssertion assertion, AsyncCallback<Profile[]> callback);
}
