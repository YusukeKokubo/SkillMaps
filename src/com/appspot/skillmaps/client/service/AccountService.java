package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface AccountService extends RemoteService {

    Login login(String requestUri);

    void putProfile(Profile act);

    Profile[] getUsers();

    Profile getUser(String id);

    Profile[] getUsersByEmail(String[] emails);

    UserListResultDto getUsers(int pn,
                                String encodedCursor,
                                String encodedFilter,
                                String encodedSorts);

    UserListResultDto getUserList();

    Profile[] getRecentEntriedUsers();

}
