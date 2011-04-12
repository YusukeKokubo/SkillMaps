package com.appspot.skillmaps.server.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ServletTestCase;

import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.appspot.skillmaps.shared.model.SkillRelation;

public class SkillServiceImplTest extends ServletTestCase {

    private SkillServiceImpl service = new SkillServiceImpl();

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
        tester.environment.setEmail("keisuke.oohashi@gmail.com");

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
}
