package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SkillServiceAsync {

    void putSkill(Skill skill, AsyncCallback<Void> callback);

    void getSkills(String ownerEmail, AsyncCallback<Skill[]> callback);

}
