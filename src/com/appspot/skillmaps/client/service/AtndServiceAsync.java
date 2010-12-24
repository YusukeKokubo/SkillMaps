package com.appspot.skillmaps.client.service;

import java.util.List;

import com.appspot.skillmaps.shared.model.AtndEvent;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AtndServiceAsync {

    void updateAtndEvents(Profile profile,
            AsyncCallback<List<AtndEvent>> callback);

    void getAtndEvents(Profile profile, AsyncCallback<List<AtndEvent>> callback);

}
