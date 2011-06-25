package com.appspot.skillmaps.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ServletTestCase;

import com.appspot.skillmaps.server.meta.FollowingMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.server.meta.SkillAssertionMeta;
import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillAgree;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.appengine.api.datastore.Key;

public class SkillServiceImplTest extends ServletTestCase {

    private SkillServiceImpl service = new SkillServiceImpl();
    ProfileMeta pm = ProfileMeta.get();
    FollowingMeta fm = FollowingMeta.get();
    SkillMeta sm = SkillMeta.get();
    SkillAssertionMeta am = SkillAssertionMeta.get();
    
    Profile a;
    Profile b;
    Profile c;
    
    @Before
    public void setup() {
        a = new Profile();
        a.setName("A");
        a.setUserEmail("A@test.com");
        b = new Profile();
        b.setUserEmail("B@test.com");
        b.setName("B");
        c = new Profile();
        c.setUserEmail("C@test.com");
        c.setName("C");
        tester.environment.setEmail("A@test.com");

        List<Key> keys = Datastore.put(a, b, c);
        List<Profile> list = Datastore.get(pm, keys);
        
        a = list.get(0);
        b = list.get(1);
        c = list.get(2);
    }

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }

    @Test
    public void Skillの表明をできること() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(b);
        SkillAssertion assertion = new SkillAssertion();
        assertion.setDescription("hogehoge");
        assertion.setUrl("http://localhost/hoge");
        
        Key key = service.putSkillAssertion(skill, assertion);
        
        SkillAssertion iedAssertion = Datastore.get(am, key);
        
        assertThat(iedAssertion.getCreatedBy().getModel(), is(a));
        assertThat(iedAssertion.getDescription(), is("hogehoge"));
        assertThat(iedAssertion.getUrl(), is("http://localhost/hoge"));
        assertThat(iedAssertion.getSkill().getModel().getName(), is("Java"));
        assertThat(iedAssertion.getSkill().getModel().getPoint(), is(1L));
        assertThat(iedAssertion.getSkill().getModel().getHolder().getModel(), is(b));
        assertThat(iedAssertion.getAgrees().getModelList().get(0).getProfile().getModel(), is(a));
    }

    @Test
    public void 自分でSkillを表明した場合はagreesには入らない() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(a);
        SkillAssertion assertion = new SkillAssertion();
        assertion.setDescription("hogehoge");
        assertion.setUrl("http://localhost/hoge");
        
        Key key = service.putSkillAssertion(skill, assertion);
        SkillAssertion iedAssertion = Datastore.get(am, key);
        
        assertThat(iedAssertion.getCreatedBy().getModel(), is(a));
        assertThat(iedAssertion.getDescription(), is("hogehoge"));
        assertThat(iedAssertion.getUrl(), is("http://localhost/hoge"));
        assertThat(iedAssertion.getSkill().getModel().getName(), is("Java"));
        assertThat(iedAssertion.getSkill().getModel().getHolder().getModel(), is(a));
        assertThat(iedAssertion.getAgrees().getModelList().size(), is(0));
    }

    @Test
    public void Skillの表明に賛同できること() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(b);
        SkillAssertion assertion = new SkillAssertion();
        assertion.setDescription("hogehoge");
        assertion.setUrl("http://localhost/hoge");
        
        Key key = service.putSkillAssertion(skill, assertion);
        SkillAssertion iedAssertion = Datastore.get(am, key);

        tester.environment.setEmail("C@test.com");
        SkillAgree agree = new SkillAgree();
        service.agree(iedAssertion, agree);
        
        assertThat(iedAssertion.getSkill().getModel().getPoint(), is(2L));
    }
}
