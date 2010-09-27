package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface SkillService extends RemoteService {

    void putSkill(Skill skill);

    Skill[] getSkills(String ownerEmail);

}
