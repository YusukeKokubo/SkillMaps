package com.appspot.skillmaps.server.service;

import java.util.List;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ServletTestCase;

import com.appspot.skillmaps.server.meta.FollowingMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.Following;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.datastore.Key;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

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
        Profile follower = new Profile();
        Profile following = new Profile();
        
        List<Key> keys = Datastore.put(follower, following);
        List<Profile> ps = Datastore.get(pm, keys);
        
        Following f = new Following();
        f.setFollowerEmail(ps.get(0).getUserEmail());
        f.setFollowingEmail(ps.get(1).getUserEmail());
        
        Profile[] fw = service.getFollower(ps.get(0));
        assertThat(fw, is(notNullValue()));
        assertThat(fw.length, is(1));
        assertThat(fw[0], is(ps.get(1)));
    }
}
