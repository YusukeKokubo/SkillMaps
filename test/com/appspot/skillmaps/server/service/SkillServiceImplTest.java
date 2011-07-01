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
import com.appspot.skillmaps.shared.model.Comment;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.SerializationException;

public class SkillServiceImplTest extends ServletTestCase {

    private SkillServiceImpl service = new SkillServiceImpl();
    ProfileMeta pm = ProfileMeta.get();
    FollowingMeta fm = FollowingMeta.get();
    SkillMeta sm = SkillMeta.get();
    SkillAssertionMeta am = SkillAssertionMeta.get();
    
    Profile a;
    Profile b;
    Profile c;
    Profile d;
    
    @Before
    public void setup() {
        a = new Profile();
        a.setUserEmail("A@test.com");
        a.setName("A");
        b = new Profile();
        b.setUserEmail("B@test.com");
        b.setName("B");
        c = new Profile();
        c.setUserEmail("C@test.com");
        c.setName("C");
        d = new Profile();
        d.setUserEmail("D@test.com");
        d.setName("D");
        tester.environment.setEmail("A@test.com");

        List<Key> keys = Datastore.put(a, b, c, d);
        List<Profile> list = Datastore.get(pm, keys);
        
        a = list.get(0);
        b = list.get(1);
        c = list.get(2);
        d = list.get(3);
    }

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
    
    @Test
    public void Skillを追加できること() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(b);
        SkillA iedSkill = service.addSkill(skill);
        
        assertThat(iedSkill.getName(), is("Java"));
        assertThat(iedSkill.getAssertions().getModelList().isEmpty(), is(true));
        assertThat(iedSkill.getCreatedBy().getModel(), is(a));
        assertThat(iedSkill.getHolder().getModel(), is(b));
        assertThat(iedSkill.getPoint(), is(0L));
    }
    
    @Test(expected=SerializationException.class)
    public void 同じ名前のSkillは追加できないこと() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(b);
        service.addSkill(skill);
        
        SkillA skill2 = new SkillA();
        skill2.setName("Java");
        skill2.getHolder().setModel(b);
        service.addSkill(skill2);
    }

    @Test
    public void SkillAssertionを追加できること() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(b);
        SkillA iedSkill = service.addSkill(skill);
        
        SkillAssertion assertion = new SkillAssertion();
        assertion.setUrl("http://yahoo.com");
        assertion.getSkill().setModel(iedSkill);
        SkillAssertion iedAssertion = service.addAssert(assertion);
        
        assertThat(iedAssertion.getCreatedBy().getModel(), is(a));
        assertThat(iedAssertion.getUrl(), is("http://yahoo.com"));
        assertThat(iedAssertion.getSkill().getModel().getName(), is("Java"));
        assertThat(iedAssertion.getSkill().getModel().getPoint(), is(1L));
        assertThat(iedAssertion.getSkill().getModel().getHolder().getModel(), is(b));
        assertThat(iedAssertion.getAgrees().get(0), is(a.getKey()));
    }
    
    @Test(expected=SerializationException.class)
    public void 同じURLのSkillAssertionは追加できないこと() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(b);
        SkillA iedSkill = service.addSkill(skill);
        
        SkillAssertion assertion = new SkillAssertion();
        assertion.setUrl("http://yahoo.com");
        assertion.getSkill().setModel(iedSkill);
        service.addAssert(assertion);

        SkillAssertion assertion2 = new SkillAssertion();
        assertion2.setUrl("http://yahoo.com");
        assertion2.getSkill().setModel(iedSkill);
        service.addAssert(assertion2);
    }

    @Test
    public void 自分でSkillを表明した場合はagreesには入らない() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(a);
        SkillA iedSkill = service.addSkill(skill);
        
        SkillAssertion assertion = new SkillAssertion();
        assertion.setUrl("http://yahoo.com");
        assertion.getSkill().setModel(iedSkill);
        SkillAssertion iedAssertion = service.addAssert(assertion);
        
        assertThat(iedAssertion.getCreatedBy().getModel(), is(a));
        assertThat(iedAssertion.getUrl(), is("http://yahoo.com"));
        assertThat(iedAssertion.getSkill().getModel().getName(), is("Java"));
        assertThat(iedAssertion.getSkill().getModel().getHolder().getModel(), is(a));
        assertThat(iedAssertion.getAgrees().size(), is(0));
    }

    @Test
    public void SkillAssertionにagreeできること() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(b);
        SkillA iedSkill = service.addSkill(skill);
        
        SkillAssertion assertion = new SkillAssertion();
        assertion.setUrl("http://yahoo.com");
        assertion.getSkill().setModel(iedSkill);
        SkillAssertion iedAssertion = service.addAssert(assertion);

        tester.environment.setEmail("C@test.com");
        SkillAssertion iedAssertionC = service.agree(iedAssertion);
        assertThat(iedAssertionC.getSkill().getModel().getPoint(), is(2L));
        assertThat(iedAssertionC.getAgrees().get(1), is(c.getKey()));

        tester.environment.setEmail("D@test.com");
        SkillAssertion iedAssertionD = service.agree(iedAssertionC);
        assertThat(iedAssertionD.getSkill().getModel().getPoint(), is(3L));
        assertThat(iedAssertionD.getAgrees().get(2), is(d.getKey()));
    }
    
    @Test(expected=SerializationException.class)
    public void agreeは複数回できないこと() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(b);
        SkillA iedSkill = service.addSkill(skill);
        
        SkillAssertion assertion = new SkillAssertion();
        assertion.setUrl("http://yahoo.com");
        assertion.getSkill().setModel(iedSkill);
        SkillAssertion iedAssertion = service.addAssert(assertion);

        tester.environment.setEmail("C@test.com");
        service.agree(service.agree(iedAssertion));
    }
    
    @Test
    public void commentできること() throws Exception {
        SkillA skill = new SkillA();
        skill.setName("Java");
        skill.getHolder().setModel(b);
        SkillA iedSkill = service.addSkill(skill);
        
        SkillAssertion assertion = new SkillAssertion();
        assertion.setUrl("http://yahoo.com");
        assertion.getSkill().setModel(iedSkill);
        SkillAssertion iedAssertion = service.addAssert(assertion);

        Comment comment = service.addComment(iedAssertion, "hogehoge");
        assertThat(comment.getAssertion().getModel(), is(iedAssertion));
        assertThat(comment.getComment(), is("hogehoge"));
        assertThat(comment.getCreatedBy().getModel(), is(a));
    }
}
