package com.appspot.skillmaps.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ServletTestCase;

import com.appspot.skillmaps.server.meta.FollowingMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.Following;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.datastore.Key;

public class AccountServiceImplTest extends ServletTestCase {

    private AccountServiceImpl service = new AccountServiceImpl();
    
    ProfileMeta pm = ProfileMeta.get();
    FollowingMeta fm = FollowingMeta.get();
    
    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
    
    @Test
    public void followingが1人いる() throws Exception {
        Profile a = new Profile();
        a.setName("follower");
        a.setUserEmail("follower@test.com");
        Profile b = new Profile();
        b.setUserEmail("following@test.com");
        b.setName("following");
        
        List<Key> keys = Datastore.put(a, b);
        List<Profile> ps = Datastore.get(pm, keys);
        
        Following f = new Following();
        f.setFromEmail(ps.get(0).getUserEmail());
        f.setToEmail(ps.get(1).getUserEmail());
        Datastore.put(f);
        
        Profile[] fw = service.getFollowingBy(ps.get(0));
        assertThat(fw.length, is(1));
        assertThat(fw[0], is(ps.get(1)));
    }
    
}
