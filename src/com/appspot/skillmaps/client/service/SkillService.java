package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface SkillService extends RemoteService {

    Skill[] getEnabledSkills(String ownerEmail);

    Skill[] getDisabledSkills(String ownerEmail);

    void putSkill(Skill skill, SkillRelation rel, boolean sendTwitter);

    void putSkillAppeal(SkillAppeal skillAppeal, boolean sendTwitter);

    SkillAppeal[] getSkillAppeals();

    SkillRelation[] getSkillRelations(Skill skill);

    Skill[] getSkillOwners(Skill skill);

    Skill[] getSkillOwners(String skillName);

    SkillMap[] getSkillNames();

    Skill[] getRecentAddedSkills();
}
