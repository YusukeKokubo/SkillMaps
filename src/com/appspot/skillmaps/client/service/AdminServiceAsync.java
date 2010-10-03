package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.GlobalSetting;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminServiceAsync {

    void getGlobalSetting(AsyncCallback<GlobalSetting> callback);

    void putGlobalSetting(GlobalSetting gs, String notifierId, AsyncCallback<Void> callback);

}
