package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface SkillService extends RemoteService {

    Skill[] getSkills(String ownerEmail);

    void putSkill(Skill skill, SkillRelation rel);

    void putSkillAppeal(SkillAppeal skillAppeal, boolean sendTwitter);

    SkillAppeal[] getSkillAppeals();
}
