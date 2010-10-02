package com.appspot.skillmaps.server.service;

import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;

import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.server.meta.SkillAppealMeta;
import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.server.meta.SkillRelationMeta;
import com.appspot.skillmaps.server.util.TwitterUtil;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillAppeal;
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
    public void putSkill(Skill skill, SkillRelation rel) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");

        if (StringUtil.isEmpty(skill.getName())) throw new IllegalArgumentException("skill name is null");

        rel.getSkill().setModel(skill);
        skill.getRelation().getModelList().add(rel);
        skill.setPoint((long) skill.getRelation().getModelList().size());
        Datastore.put(skill, rel);
    }

    @Override
    public Skill[] getSkills(String ownerEmail) {
        List<Skill> result = Datastore.query(sm).filter(sm.ownerEmail.equal(ownerEmail)).asList();
        for (Skill s : result) {
            for (SkillRelation sr : s.getRelation().getModelList()) {
                sr.setProfile(Datastore.query(pm).filter(pm.userEmail.equal(sr.getUserEmail())).asSingle());
            }
        }
        return result.toArray(new Skill[0]);
    }

    @Override
    public SkillAppeal[] getSkillAppeals() {
        List<SkillAppeal> result = Datastore.query(am).sort(am.createdAt.desc).limit(20).asList();
        for (SkillAppeal appeal : result) {
            appeal.setProfile(Datastore.query(pm).filter(pm.userEmail.equal(appeal.getUserEmail())).asSingle());
        }
        return result.toArray(new SkillAppeal[0]);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void putSkillAppeal(SkillAppeal skillAppeal, boolean sendTwitter) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");

        Datastore.put(skillAppeal);
        // Twitterに送信
        if(sendTwitter){
            Twitter twitter = TwitterUtil.getTwitterInstance();
            AccessToken accessToken = TwitterUtil.loadAccessToken();
            twitter.setOAuthAccessToken(accessToken);
            String skillmapsUrl = "http://j.mp/dfQBqk";
            String description = skillAppeal.getDescription();
            try{
                if(description != null && description.length() > 108){
                    description = skillAppeal.getDescription().substring(0, 108) + "…";
                }
                twitter.updateStatus(description + " #skillmaps " + skillmapsUrl);
            }catch(TwitterException te){
                throw new IllegalArgumentException("Oops, there must be something wrong with Twitter.");
            }
        }
        
    }
}
