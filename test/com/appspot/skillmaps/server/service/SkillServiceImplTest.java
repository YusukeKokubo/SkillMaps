package com.appspot.skillmaps.server.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ServletTestCase;

import com.appspot.skillmaps.server.meta.FollowingMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.server.meta.SkillCommentMeta;
import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.shared.model.Following;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.datastore.Key;

public class SkillServiceImplTest extends ServletTestCase {

    private SkillServiceImpl service = new SkillServiceImpl();
    ProfileMeta pm = ProfileMeta.get();
    FollowingMeta fm = FollowingMeta.get();
    SkillMeta sm = SkillMeta.get();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }

    @Test
    public void putSkill_コメントなし(){
        tester.environment.setEmail("keisuke.oohashi@gmail.com");

        Skill skill = new Skill();
        skill.setName("test");
        skill.setDescription("test2");
        skill.setOwnerEmail("test@admin.com");
        SkillRelation skillRelation = new SkillRelation();

        service.putSkill(skill, skillRelation, false);
        Skill resultSkill = Datastore.getOrNull(Skill.class ,skill.getKey());
        assertThat(resultSkill, notNullValue());
        List<SkillRelation> relList = resultSkill.getRelation().getModelList();
        assertThat(relList , notNullValue());
        assertThat(relList.size() ,is(1));
        assertThat(relList.get(0).getKey(), is(skillRelation.getKey()));
        assertThat(Datastore.query(SkillComment.class).asList().size(), is(0));
    }

    @Test
    public void putSkill_コメントあり(){
        tester.environment.setEmail("test@gmail.com");

        Skill skill = new Skill();
        skill.setName("test");
        skill.setDescription("test2");
        skill.setOwnerEmail("test@admin.com");
        SkillRelation skillRelation = new SkillRelation();

        service.putSkill(skill, skillRelation , "コメント", false);
        Skill resultSkill = Datastore.getOrNull(Skill.class ,skill.getKey());
        assertThat(resultSkill, notNullValue());
        List<SkillRelation> relList = resultSkill.getRelation().getModelList();
        assertThat(relList , notNullValue());
        assertThat(relList.size() ,is(1));
        assertThat(relList.get(0).getKey(), is(skillRelation.getKey()));
        assertThat(Datastore.query(SkillComment.class).asList().size(), is(1));
        List<SkillComment> commentList = skill.getCommentRel().getModelList();

        assertThat(commentList, notNullValue());
        assertThat(commentList.get(0).getComment(), is("コメント"));
        assertThat(commentList.get(0).getSkill().getModel(), is(equalTo(skill)));
    }

    @Test
    public void putComment(){
        tester.environment.setEmail("test@gmail.com");

        Skill skill = new Skill();
        skill.setName("test");
        skill.setDescription("test2");
        skill.setOwnerEmail("test@admin.com");
        SkillRelation skillRelation = new SkillRelation();

        service.putSkill(skill, skillRelation , "コメント", false);
        skill = service.getEnabledSkills(Datastore.query(pm).filter(pm.userEmail.equal("test@admin.com")).asSingle())[0];
        service.putComment(skill.getKey(), "コメント2");
        service.putComment(null, "コメント3");
        service.putComment(skill.getKey(), "");
        service.putComment(skill.getKey(), null);
        tester.environment.setEmail("");
        service.putComment(skill.getKey(), "コメント4");

        List<SkillComment> modelList = skill.getCommentRel().getModelList();
        assertThat(modelList , notNullValue());
        assertThat(modelList.size(), is(2));
        assertThat(modelList.get(0).getComment(), is("コメント2"));
        assertThat(modelList.get(0).getSkill().getModel(), equalTo(skill));
        assertThat(modelList.get(1).getComment(), is("コメント"));
        assertThat(modelList.get(1).getSkill().getModel(), equalTo(skill));
        assertThat(Datastore.query(SkillCommentMeta.get()).asList().size(), is(2));
    }


    @Test
    public void putSkillすればfollowしたことになる() throws Exception {
        Profile a = new Profile();
        a.setName("A");
        a.setUserEmail("A@test.com");
        Profile b = new Profile();
        b.setUserEmail("B@test.com");
        b.setName("B");
        tester.environment.setEmail("A@test.com");

        List<Key> keys = Datastore.put(a, b);
        Datastore.get(pm, keys);

        Skill skill = new Skill();
        skill.setName("hogeSkill");
        skill.setOwnerEmail("B@test.com");
        SkillRelation rel = new SkillRelation();
        service.putSkill(skill, rel, false);

        List<Following> fw = Datastore.query(fm).asList();
        // なぜかDatastoreに1件不明なデータが入っているのでへんな参照になっている
        assertThat(fw.get(1).getFromEmail(), is("A@test.com"));
        assertThat(fw.get(1).getToEmail(), is("B@test.com"));
    }

}
