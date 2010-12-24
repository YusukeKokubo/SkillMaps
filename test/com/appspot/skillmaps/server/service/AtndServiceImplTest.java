package com.appspot.skillmaps.server.service;

import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ServletTestCase;
import org.junit.Test;

import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.AtndEvent;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.datastore.Key;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AtndServiceImplTest extends ServletTestCase {

    private static final ProfileMeta pm = ProfileMeta.get();
    
    private AtndServiceImpl service = new AtndServiceImpl();

    @Test
    public void 保存できること() throws Exception {
        Profile p1 = new Profile();
        p1.setId("yusuke_kokubo");
        p1.setName("yusuke kokubo");
        p1.setTwitterScreenName("yusuke_kokubo");
        p1.setTwitterToken("xxxx");
        p1.setTwitterTokenSecret("yyyy");

        Key k = Datastore.put(p1);
        Profile insertedP1 = Datastore.get(pm, k);
        
        List<AtndEvent> events = service.updateAtndEvents(insertedP1);
        assertThat(events, is(notNullValue()));
        assertThat(events.isEmpty(), is(false));
        for (AtndEvent e : events) {
            assertThat(e.getUsers().getModelList().size(), is(1));
        }
    }

    @Test
    public void 取得できること() throws Exception {
        Profile p1 = new Profile();
        p1.setId("yusuke_kokubo");
        p1.setName("yusuke kokubo");
        p1.setTwitterScreenName("yusuke_kokubo");
        p1.setTwitterToken("xxxx");
        p1.setTwitterTokenSecret("yyyy");

        Key k = Datastore.put(p1);
        Profile insertedP1 = Datastore.get(pm, k);
        
        service.updateAtndEvents(insertedP1);
        List<AtndEvent> events = service.getAtndEvents(insertedP1);
        assertThat(events, is(notNullValue()));
        assertThat(events.isEmpty(), is(false));
        for (AtndEvent e : events) {
            assertThat(e.getUsers().getModelList().size(), is(1));
        }
    }
}
