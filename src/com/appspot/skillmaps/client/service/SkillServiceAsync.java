package com.appspot.skillmaps.client.service;

import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SkillServiceAsync {

    void getEnabledSkills(String ownerEmail, AsyncCallback<Skill[]> callback);

    void getDisabledSkills(String ownerEmail, AsyncCallback<Skill[]> callback);

    void putSkill(Skill skill, SkillRelation rel, String comment, boolean sendTwitter,
            AsyncCallback<Void> callback);

    void putSkillAppeal(SkillAppeal skillAppeal, boolean sendTwitter, AsyncCallback<Void> callback);

    void getSkillAppeals(AsyncCallback<SkillAppeal[]> callback);

    void getSkillRelations(Skill skill, AsyncCallback<SkillRelation[]> callback);

    void getSkillOwners(Skill skill, AsyncCallback<Skill[]> callback);

    void getSkillOwners(String skillName, AsyncCallback<Skill[]> callback);

    void getSkillNames(AsyncCallback<SkillMap[]> callback);

    void getRecentAddedSkills(AsyncCallback<Skill[]> callback);

    void putSkill(Skill skill, SkillRelation rel, boolean sendTwitter, AsyncCallback<Void> asyncCallback);

}
