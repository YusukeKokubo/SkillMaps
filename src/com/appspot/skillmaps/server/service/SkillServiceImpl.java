package com.appspot.skillmaps.server.service;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SkillServiceImpl implements SkillService {
    SkillMeta sm = SkillMeta.get();

    @Override
    public void putSkill(Skill skill, SkillRelation rel) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");

        rel.getSkill().setModel(skill);
        skill.getRelation().getModelList().add(rel);
        skill.setPoint((long) skill.getRelation().getModelList().size());
        Datastore.put(skill, rel);
    }

    @Override
    public Skill[] getSkills(String ownerEmail) {
        List<Skill> result = Datastore.query(sm).filter(sm.ownerEmail.equal(ownerEmail)).asList();
        for (Skill s : result) {
            s.getRelation().getModelList();
        }
        return result.toArray(new Skill[0]);
    }
}
