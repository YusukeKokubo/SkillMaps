package com.appspot.skillmaps.shared.model;

import org.slim3.tester.AppEngineTestCase;

public class AtndEventTest extends AppEngineTestCase {

import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.slim3.util.StringUtil;
import org.hkzo.atnd4j.Atnd;
import org.hkzo.atnd4j.Event;
import org.hkzo.atnd4j.EventsResult;
import org.hkzo.atnd4j.User;
import org.junit.Before;
import org.junit.Test;

import com.appspot.skillmaps.server.meta.AtndEventMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.google.appengine.api.datastore.Key;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AtndEventTest extends AppEngineTestCase {

    private static final ProfileMeta pm = ProfileMeta.get();
    private static final AtndEventMeta m = AtndEventMeta.get();
    
    @Before
    public void setup() {
        Profile p1 = new Profile();
        p1.setName("yusuke kokubo");
        p1.setTwitterScreenName("yusuke_kokubo");

        Profile p2 = new Profile();
        p2.setName("bleis");
        p2.setTwitterScreenName("bleis");
        
        Datastore.put(p1, p2);
    }
    
    @Test
    public void 保存できること() throws Exception {
        
        Atnd atnd = new Atnd();
        EventsResult atndResults = atnd.getEventsUsers("twitter_id=yusuke_kokubo");
        
        for (Event event : atndResults.getEvents()) {
            AtndEvent aEvent = new AtndEvent();
            aEvent.setEvent(event);
            
            Key k = Datastore.put(aEvent);
    
            for (User u : event.getUsers()) {
                if (StringUtil.isEmpty(u.getTwitterId())) continue;
                Profile p = Datastore.query(pm).filter(pm.twitterScreenName.equal(u.getTwitterId())).asSingle();
                if (p == null) continue;
                AtndEventUser user = new AtndEventUser();
                user.getEvent().setModel(aEvent);
                user.getProfile().setModel(p);
                user.setStatus(u.getStatus());
                Datastore.put(user);
            }
            
            AtndEvent inserted = Datastore.get(m, k);
            assertThat(inserted.getEvent(), is(notNullValue()));
            assertThat(inserted.getEvent().getUsers().isEmpty(), is(false));
            
            List<AtndEventUser> insertedUsers = inserted.getUsers().getModelList();
            for (AtndEventUser u : insertedUsers) {
                assertThat(u.getProfile().getModel().getName(), is(notNullValue()));
            }
        }
    }
}
