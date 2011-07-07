package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Comment;
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
import com.google.gwt.user.client.rpc.SerializationException;

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

    SkillA addSkill(SkillA skill) throws SerializationException;

    SkillAssertion addAssert(SkillAssertion assertion) throws SerializationException;

    SkillAssertion agree(SkillAssertion assertion) throws SerializationException;

    SkillA[] getSkill(Profile profile);

    SkillAssertion disagree(SkillAssertion sassertion);

    SkillAssertion[] getTimeLine();

    Comment addComment(SkillAssertion assertion, String body)
            throws SerializationException;

    Comment[] getComments(SkillAssertion sa);

    SkillAssertion[] getAssertions(SkillA skill);
}
