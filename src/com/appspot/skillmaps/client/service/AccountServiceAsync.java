package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AccountServiceAsync {

    void login(String requestUri, AsyncCallback<Login> callback);

    void putProfile(Profile act, AsyncCallback<Void> callback);

    void getUsers(AsyncCallback<Profile[]> callback);

    void getUser(String id, AsyncCallback<Profile> callback);

    void getUsersByEmail(String[] emails, AsyncCallback<Profile[]> callback);

}
