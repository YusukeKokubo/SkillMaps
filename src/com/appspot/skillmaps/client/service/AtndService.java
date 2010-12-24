package com.appspot.skillmaps.client.service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.appspot.skillmaps.shared.model.AtndEvent;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface AtndService extends RemoteService {

    List<AtndEvent> updateAtndEvents(Profile profile) throws SocketTimeoutException, IllegalArgumentException, IOException;

    List<AtndEvent> getAtndEvents(Profile profile);

}
