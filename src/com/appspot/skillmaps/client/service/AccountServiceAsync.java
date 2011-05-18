package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AccountServiceAsync {

    void login(String requestUri, AsyncCallback<Login> callback);

    void putProfile(Profile act, AsyncCallback<Void> callback);

    void getUsers(AsyncCallback<Profile[]> callback);

    void getUser(String id, AsyncCallback<Profile> callback);

    void getUsersByEmail(String[] emails, AsyncCallback<Profile[]> callback);

    void getUsers(int pn, String encodedCursor, String encodedFilter,
            String encodedSorts, AsyncCallback<UserListResultDto> callback);

    void getUserList(AsyncCallback<UserListResultDto> callback);

    void getRecentEntriedUsers(AsyncCallback<Profile[]> callback);

    void getSignUrl(String backUrl, AsyncCallback<String> callback);

    void getFriends(Profile p, AsyncCallback<Profile[]> callback);

    void getFollowingBy(Profile p, AsyncCallback<Profile[]> callback);

    void getFollowerTo(Profile p, AsyncCallback<Profile[]> callback);

    void getFriends(AsyncCallback<Profile[]> callback);
}
