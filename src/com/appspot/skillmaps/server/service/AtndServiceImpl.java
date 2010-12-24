package com.appspot.skillmaps.server.service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.hkzo.atnd4j.Atnd;
import org.hkzo.atnd4j.Event;
import org.hkzo.atnd4j.EventsResult;
import org.hkzo.atnd4j.User;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;
import org.slim3.util.StringUtil;

import com.appspot.skillmaps.client.service.AtndService;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.AtndEvent;
import com.appspot.skillmaps.shared.model.AtndEventUser;
import com.appspot.skillmaps.shared.model.Profile;

public class AtndServiceImpl implements AtndService {

    private static final ProfileMeta pm = ProfileMeta.get();
    
    @Override
    public List<AtndEvent> updateAtndEvents(Profile profile) throws SocketTimeoutException, IllegalArgumentException, IOException {
        if (!profile.isEnabledTwitter()) throw new IllegalArgumentException("profile " + profile.getId() + " is not enable for twitter.");

        List<AtndEvent> result = new ArrayList<AtndEvent>();
        Atnd atnd = new Atnd();
        EventsResult atndResults = atnd.getEventsUsers("twitter_id=" + profile.getTwitterScreenName());
        
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        for (Event event : atndResults.getEvents()) {
            AtndEvent aEvent = new AtndEvent();
            aEvent.setEvent(event);
            result.add(aEvent);

            gtx.put(aEvent);
            for (User u : event.getUsers()) {
                if (StringUtil.isEmpty(u.getTwitterId())) continue;
                Profile p = Datastore.query(pm).filter(pm.twitterScreenName.equal(u.getTwitterId())).asSingle();
                if (p == null) continue;
                AtndEventUser user = new AtndEventUser();
                user.getEvent().setModel(aEvent);
                user.getProfile().setModel(p);
                user.setStatus(u.getStatus());
                gtx.put(user);
            }
        }
        gtx.commit();
        return result;
    }
    
    @Override
    public List<AtndEvent> getAtndEvents(Profile profile) {

        List<AtndEvent> result = new ArrayList<AtndEvent>();
        for (AtndEventUser e : profile.getAtndEvents().getModelList()) {
            result.add(e.getEvent().getModel());
        }
        
        return result;
    }
}
