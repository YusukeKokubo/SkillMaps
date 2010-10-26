package com.appspot.skillmaps.server.service;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;
import org.slim3.util.StringUtil;

import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.server.meta.SkillAppealMeta;
import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.server.meta.SkillRelationMeta;
import com.appspot.skillmaps.server.util.TwitterUtil;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SkillServiceImpl implements SkillService {
    SkillMeta sm = SkillMeta.get();
    SkillRelationMeta rm = SkillRelationMeta.get();
    SkillAppealMeta am = SkillAppealMeta.get();
    ProfileMeta pm = ProfileMeta.get();

    @Override
    public void putSkill(Skill skill, SkillRelation rel, boolean sendTwitter) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");

        if (StringUtil.isEmpty(skill.getName())) throw new IllegalArgumentException("skill name is null");

        // if return already exist.
        if (Datastore.query(rm)
                .filter(rm.userEmail.equal(user.getEmail()))
                .filter(rm.skill.equal(skill.getKey()))
                .limit(1).countQuickly() > 0) {
            return;
        }
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        boolean complete = false;
        while(!complete){
            try{
                Skill putSkill = null;
                if(skill.getKey() != null){
                    putSkill = gtx.get(sm , skill.getKey());
                }else{
                    putSkill = skill;
                }
                rel.getSkill().setModel(putSkill);
                putSkill.getRelation().getModelList().add(rel);
                putSkill.setPoint((long) putSkill.getRelation().getModelList().size());
                gtx.put(putSkill, rel);
                gtx.commit();
                complete = true;

                if (sendTwitter) {
                    TwitterUtil.tweetSkillAppended(putSkill);
                }
            }catch(ConcurrentModificationException cme){

            }
        }
    }

    @Override
    public Skill[] getSkillOwners(Skill skill) {
        List<Skill> skills = Datastore.query(sm).filter(sm.name.equal(skill.getName())).asList();
        for (Skill s : skills) {
            s.setProfile(Datastore.query(pm).filter(pm.userEmail.equal(s.getOwnerEmail())).limit(1).asSingle());
        }
        return skills.toArray(new Skill[0]);
    }

    @Override
    public Skill[] getSkillOwners(String skillName) {
        Skill aSkill = Datastore.query(sm).filter(sm.name.equal(skillName)).limit(1).asSingle();
        if (aSkill == null) return null;
        return this.getSkillOwners(aSkill);
    }

    @Override
    public Skill[] getSkills(String ownerEmail) {
        List<Skill> result = Datastore.query(sm).filter(sm.ownerEmail.equal(ownerEmail)).asList();
        return result.toArray(new Skill[0]);
    }
    
    @Override
    public Skill[] getRecentAddedSkills() {
        List<Skill> result = Datastore.query(sm).sort(sm.createdAt.desc).limit(20).asList();
        for (Skill s : result) {
            s.setProfile(Datastore.query(pm).filter(pm.userEmail.equal(s.getOwnerEmail())).limit(1).asSingle());
        }
        return result.toArray(new Skill[0]);
    }

    @Override
    public SkillMap[] getSkillNames() {
        List<Skill> list = Datastore.query(sm).sortInMemory(sm.name.asc).asList();

        HashMap<String, SkillMap> skillNames = new HashMap<String, SkillMap>();
        for (Skill skill : list) {
            SkillMap sm = null;
            if (skillNames.containsKey(skill.getName())) {
                sm = skillNames.get(skill.getName());
                sm.setPoint(sm.getPoint() + skill.getPoint());
            } else {
                sm = new SkillMap();
                sm.setSkillName(skill.getName());
                sm.setPoint(skill.getPoint());
            }
            skillNames.put(skill.getName(), sm);
        }

        return skillNames.values().toArray(new SkillMap[skillNames.size()]);
    }
    
    @Override
    public SkillRelation[] getSkillRelations(Skill skill) {
        for (SkillRelation sr : skill.getRelation().getModelList()) {
            Profile profile = Datastore.query(pm).filter(pm.userEmail.equal(sr.getUserEmail())).limit(1).asSingle();
            sr.setProfile(profile);
        }
        return skill.getRelation().getModelList().toArray(new SkillRelation[0]);
    }

    @Override
    public SkillAppeal[] getSkillAppeals() {
        List<SkillAppeal> result = Datastore.query(am).sort(am.createdAt.desc).limit(20).asList();
        for (SkillAppeal appeal : result) {
            Profile profile = Datastore.query(pm).filter(pm.userEmail.equal(appeal.getUserEmail())).limit(1).asSingle();
            appeal.setProfile(profile);
        }
        return result.toArray(new SkillAppeal[0]);
    }

    @Override
    public void putSkillAppeal(SkillAppeal skillAppeal, boolean sendTwitter) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");

        Datastore.put(skillAppeal);
        // Twitterに送信
        if(sendTwitter){
            TwitterUtil.tweetSkillAppeal(skillAppeal);
        }
    }
}
