package com.appspot.skillmaps.server.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import net.htmlparser.jericho.CharacterReference;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;
import org.slim3.util.StringUtil;

import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.server.meta.FollowingMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.server.meta.SkillAMeta;
import com.appspot.skillmaps.server.meta.SkillAppealMeta;
import com.appspot.skillmaps.server.meta.SkillAssertionMeta;
import com.appspot.skillmaps.server.meta.SkillCommentMeta;
import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.server.meta.SkillRelationMeta;
import com.appspot.skillmaps.server.util.TwitterUtil;
import com.appspot.skillmaps.shared.model.Following;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.base.Strings;
import com.google.gwt.user.client.rpc.SerializationException;

public class SkillServiceImpl implements SkillService {
    SkillMeta sm = SkillMeta.get();
    SkillRelationMeta rm = SkillRelationMeta.get();
    SkillAppealMeta am = SkillAppealMeta.get();
    ProfileMeta pm = ProfileMeta.get();
    SkillCommentMeta scm = SkillCommentMeta.get();
    FollowingMeta fm = FollowingMeta.get();
    SkillAMeta sma = SkillAMeta.get();
    SkillAssertionMeta sam = SkillAssertionMeta.get();

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
    public Skill[] getEnabledSkills(Profile profile) {

        profile = Datastore.get(pm, profile.getKey());

        List<Skill> result = Datastore.query(sm).filter(sm.ownerEmail.equal(profile.getUserEmail())).filter(sm.enable.equal(true)).asList();
        return result.toArray(new Skill[0]);
    }

    @Override
    public Skill[] getDisabledSkills(Profile profile) {
        profile = Datastore.get(pm, profile.getKey());

        List<Skill> result = Datastore.query(sm).filter(sm.ownerEmail.equal(profile.getUserEmail())).filter(sm.enable.equal(false)).asList();
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
    public void putSkill(Skill skill, SkillRelation rel) {
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

                    if(putSkill.getOwnerEmail() == null) {
                        Profile profile = putSkill.getProfile();

                        if(profile == null) {
                            throw new IllegalArgumentException("データの状態に問題があります。");
                        }

                        profile = gtx.get(pm,profile.getKey());

                        putSkill.setOwnerEmail(profile.getUserEmail());
                    }
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

                putSkill.setProfile(null);
                putSkill.calcPoint();
                putSkill.setAgreedCount((long) putSkill.getRelation().getModelList().size());

                gtx.put(putSkill, rel);

                // follow
                Following follow = Datastore.query(fm)
                             .filter(fm.fromEmail.equal(user.getEmail()))
                             .filter(fm.toEmail.equal(putSkill.getOwnerEmail())).limit(1).asSingle();
                if (follow == null) {
                    follow = new Following();
                    follow.setFromEmail(user.getEmail());
                    follow.setToEmail(putSkill.getOwnerEmail());
                    gtx.put(follow);
                }

                gtx.commit();
                complete = true;

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

    @Override
    public SkillA addSkill(SkillA skill) throws SerializationException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new SerializationException("the user is null");
        
        if (Datastore.query(sma)
                .filter(sma.holder.equal(skill.getHolder().getKey()))
                .filter(sma.name.equal(skill.getName())).count() > 0) {
            throw new SerializationException(String.format("the skill [%s] is already added.", skill.getName()));
        }

        Profile profile = Datastore.query(pm).filter(pm.userEmail.equal(user.getEmail())).limit(1).asSingle();
        skill.getCreatedBy().setModel(profile);
        skill.setPoint(0L);
        
        return Datastore.get(sma, Datastore.put(skill));
    }
    
    @Override
    public SkillAssertion addAssert(SkillAssertion assertion) throws SerializationException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new SerializationException("the user is null");

        // validation distinct url
        if (Datastore.query(sam)
                .filter(sam.skill.equal(assertion.getSkill().getKey()))
                .filter(sam.url.equal(assertion.getUrl())).count() > 0) {
            throw new SerializationException(
                String.format("the url [%s] to skill [%s] is already added."
                    , assertion.getUrl()
                    , assertion.getSkill().getModel().getName()));
        }
        
        // validation correct url
        try {
            Source source = new Source(new URL(assertion.getUrl()));
            Element titleElement=source.getFirstElement(HTMLElementName.TITLE);
            if (titleElement == null) return null;
            assertion.setDescription(CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        Profile profile = Datastore.query(pm).filter(pm.userEmail.equal(user.getEmail())).limit(1).asSingle();
        assertion.getCreatedBy().setModel(profile);
        
        if (!assertion.isCreatedByOwn()) {
            agree(assertion);
        }
        
        return Datastore.get(sam, Datastore.put(assertion));
    }

    @Override
    public SkillAssertion agree(SkillAssertion assertion) throws SerializationException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new SerializationException("the user is null");

        Profile profile = Datastore.query(pm).filter(pm.userEmail.equal(user.getEmail())).limit(1).asSingle();
        if (assertion.getAgrees().indexOf(profile.getKey()) > -1) {
            throw new SerializationException("the user is already agreed");
        }
        assertion.getAgrees().add(profile.getKey());
        Key result = Datastore.put(assertion);
        
        SkillA skill = assertion.getSkill().getModel();
        skill.setPoint(skill.calcPoint());
        Datastore.put(skill);
        
        return Datastore.get(sam, result);
    }

    @Override
    public SkillA[] getSkill(Profile profile) {
        return Datastore.query(sma).filter(sma.holder.equal(profile.getKey())).asList().toArray(new SkillA[0]);
    }

    @Override
    public SkillAssertion[] getAssertion(SkillA skill) {
        return skill.getAssertions().getModelList().toArray(new SkillAssertion[0]);
    }

    @Override
    public SkillAssertion disagree(SkillAssertion sassertion) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");

        Profile profile = Datastore.query(pm).filter(pm.userEmail.equal(user.getEmail())).limit(1).asSingle();
        sassertion.getAgrees().remove(profile.getKey());
        
        return Datastore.get(sam, Datastore.put(sassertion));
    }

    @Override
    public SkillAssertion[] getTimeLine() {
        List<SkillAssertion> assertions = Datastore.query(sam).limit(10).sort(sam.updatedAt.desc).asList();
        for (SkillAssertion sa : assertions) {
            sa.getSkill().getModel().getHolder().getModel();
        }
        return assertions.toArray(new SkillAssertion[0]);
    }
}
