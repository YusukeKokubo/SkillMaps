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
import com.appspot.skillmaps.server.meta.SkillCommentMeta;
import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.server.meta.SkillRelationMeta;
import com.appspot.skillmaps.server.util.TwitterUtil;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.base.Strings;

public class SkillServiceImpl implements SkillService {
    SkillMeta sm = SkillMeta.get();
    SkillRelationMeta rm = SkillRelationMeta.get();
    SkillAppealMeta am = SkillAppealMeta.get();
    ProfileMeta pm = ProfileMeta.get();
    SkillCommentMeta scm = SkillCommentMeta.get();


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
    public Skill[] getEnabledSkills(String ownerEmail) {
        List<Skill> result = Datastore.query(sm).filter(sm.ownerEmail.equal(ownerEmail)).filter(sm.enable.equal(true)).asList();
        return result.toArray(new Skill[0]);
    }

    @Override
    public Skill[] getDisabledSkills(String ownerEmail) {
        List<Skill> result = Datastore.query(sm).filter(sm.ownerEmail.equal(ownerEmail)).filter(sm.enable.equal(false)).asList();
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
        AccountServiceImpl accountService = new AccountServiceImpl();
        for (SkillRelation sr : skill.getRelation().getModelList()) {
            Profile profile = accountService.getUserByEmail(sr.getUserEmail());
            sr.setProfile(profile);
        }
        return skill.getRelation().getModelList().toArray(new SkillRelation[0]);
    }

    @Override
    public SkillAppeal[] getSkillAppeals() {
        List<SkillAppeal> result = Datastore.query(am).sort(am.createdAt.desc).limit(20).asList();
        AccountServiceImpl accountService = new AccountServiceImpl();
        for (SkillAppeal appeal : result) {
            Profile profile = accountService.getUserByEmail(appeal.getUserEmail());
            appeal.setProfile(profile);
        }
        return result.toArray(new SkillAppeal[0]);
    }

    @Override
    public SkillComment[] getSkillComments(Key skillKey){

        List<SkillComment> list = Datastore.query(scm).filter(scm.skill.equal(skillKey)).sort(scm.createdAt.desc).asList();

        AccountServiceImpl accountService = new AccountServiceImpl();

        for (SkillComment skillComment : list) {
            Profile profile = accountService.getUserByEmail(skillComment.getCreatedUserEmail());

            skillComment.setProfile(profile);
        }

        return list.toArray(new SkillComment[0]);
    }

    @Override
    public SkillComment[] getRecentAddedSkillComment(){
        List<SkillComment> result = Datastore.query(scm).sort(scm.createdAt.desc).limit(20).asList();

        AccountServiceImpl accountService = new AccountServiceImpl();

        for (SkillComment s : result) {
            //TODO ここ厳しい。。。
            s.setProfile(accountService.getUserByEmail(s.getCreatedUserEmail()));
            s.getSkill().getModel();
            s.getSkill().getModel().setProfile(accountService.getUserByEmail(s.getSkill().getModel().getOwnerEmail()));
        }

        return result.toArray(new SkillComment[0]);

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

    @Override
    public void putSkill(Skill skill, SkillRelation rel, boolean sendTwitter) {
        putSkill(skill, rel, null, sendTwitter);
    }

    @Override
    public void putSkill(Skill skill, SkillRelation rel, String comment, boolean sendTwitter) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");

        if (StringUtil.isEmpty(skill.getName())) throw new IllegalArgumentException("skill name is null");

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
                putSkill.setEnable(true);
                if (rel.getKey() == null) {
                    rel.getSkill().setModel(putSkill);
                    putSkill.getRelation().getModelList().add(rel);
                } else {
                    // ポイントの計算をうまくやるためにここで小細工してる
                    putSkill.getRelation().getModelList().remove(rel);
                    putSkill.getRelation().getModelList().add(rel);
                }
                putSkill.calcPoint();
                putSkill.setAgreedCount((long) putSkill.getRelation().getModelList().size());

                if(!Strings.isNullOrEmpty(comment)){
                    SkillComment skillComment = new SkillComment();
                    skillComment.setComment(comment);
                    skillComment.getSkill().setModel(putSkill);
                    gtx.put(putSkill, rel , skillComment);
                } else {

                    gtx.put(putSkill, rel);
                }

                gtx.commit();
                complete = true;

                if (sendTwitter) {
                    TwitterUtil.tweetSkillAppended(putSkill);
                }
            }catch(ConcurrentModificationException cme){
            }
        }

    }

    public SkillComment putComment(Key skillKey , String comment){
        if(Strings.isNullOrEmpty(comment)){
            throw new IllegalArgumentException("コメントがカラです。");
        }

        if(skillKey == null){
            throw new IllegalArgumentException("不明な例外です。");
        }

        UserService userService = UserServiceFactory.getUserService();
        if(!userService.isUserLoggedIn()){
            throw new IllegalArgumentException("ログインしていません。");
        }

        SkillComment skillComment = new SkillComment();
        skillComment.setComment(comment);
        skillComment.getSkill().setKey(skillKey);

        Datastore.put(skillComment);

        skillComment.setProfile(new AccountServiceImpl().getUserByEmail(userService.getCurrentUser().getEmail()));

        return skillComment;
    }
}
