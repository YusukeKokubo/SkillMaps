package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface SkillService extends RemoteService {

    Skill[] getEnabledSkills(Profile profile);

    Skill[] getDisabledSkills(Profile profile);

    void putSkillAppeal(SkillAppeal skillAppeal, boolean sendTwitter);

    SkillAppeal[] getSkillAppeals();

    SkillRelation[] getSkillRelations(Skill skill);

    Skill[] getSkillOwners(Skill skill);

    Skill[] getSkillOwners(String skillName);

    SkillMap[] getSkillNames();

    Skill[] getRecentAddedSkills();

    void putSkill(Skill skill, SkillRelation rel, String comment,boolean sendTwitter);

    void putSkill(Skill skill, SkillRelation rel, boolean sendTwitter);

    SkillComment putComment(Key skillKey , String comment);

    SkillComment[] getSkillComments(Key skillKey);

    SkillComment[] getRecentAddedSkillComment();
}
