package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.GlobalSetting;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface AdminService extends RemoteService {

    GlobalSetting getGlobalSetting();

    void putGlobalSetting(GlobalSetting gs, String notifierId);
}
