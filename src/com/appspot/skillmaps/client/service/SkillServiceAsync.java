package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SkillServiceAsync {

    void getSkills(String ownerEmail, AsyncCallback<Skill[]> callback);

    void putSkill(Skill skill, SkillRelation rel, AsyncCallback<Void> callback);

    void putSkillAppeal(SkillAppeal skillAppeal, AsyncCallback<Void> callback);

    void getSkillAppeals(AsyncCallback<SkillAppeal[]> callback);

}