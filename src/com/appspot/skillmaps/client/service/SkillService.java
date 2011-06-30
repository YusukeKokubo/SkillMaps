package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.appspot.skillmaps.shared.model.SkillAssertion;
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

    SkillComment putComment(Key skillKey , String comment);

    SkillComment[] getSkillComments(Key skillKey);

    SkillComment[] getRecentAddedSkillComment();

    void putSkill(Skill skill, SkillRelation rel);

    SkillA addSkill(SkillA skill);

    SkillAssertion addAssert(SkillAssertion assertion);

    SkillAssertion agree(SkillAssertion assertion);

    SkillA[] getSkill(Profile profile);

    SkillAssertion[] getAssertion(SkillA skill);

    SkillAssertion disagree(SkillAssertion sassertion);

    SkillAssertion[] getTimeLine();
}
