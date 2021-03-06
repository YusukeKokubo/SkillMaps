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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SkillServiceAsync {

    void getEnabledSkills(Profile profile, AsyncCallback<Skill[]> callback);

    void getDisabledSkills(Profile profile, AsyncCallback<Skill[]> callback);

    void putSkillAppeal(SkillAppeal skillAppeal, boolean sendTwitter, AsyncCallback<Void> callback);

    void getSkillAppeals(AsyncCallback<SkillAppeal[]> callback);

    void getSkillRelations(Skill skill, AsyncCallback<SkillRelation[]> callback);

    void getSkillOwners(Skill skill, AsyncCallback<Skill[]> callback);

    void getSkillOwners(String skillName, AsyncCallback<Skill[]> callback);

    void getSkillNames(AsyncCallback<SkillMap[]> callback);

    void getRecentAddedSkills(AsyncCallback<Skill[]> callback);

    void putComment(Key skillKey, String comment, AsyncCallback<SkillComment> callback);

    void getSkillComments(Key skillKey, AsyncCallback<SkillComment[]> callback);

    void getRecentAddedSkillComment(AsyncCallback<SkillComment[]> callback);

    void putSkill(Skill skill, SkillRelation rel, AsyncCallback<Void> callback);

    void addSkill(SkillA skill, AsyncCallback<SkillA> callback);

    void addAssert(SkillAssertion assertion,
            AsyncCallback<SkillAssertion> callback);

    void agree(SkillAssertion assertion, AsyncCallback<SkillAssertion> callback);

    void getSkill(Profile profile, AsyncCallback<SkillA[]> callback);

    void disagree(SkillAssertion sassertion,
            AsyncCallback<SkillAssertion> asyncCallback);

    void getTimeLine(AsyncCallback<SkillAssertion[]> asyncCallback);

    void addComment(SkillAssertion assertion, String body,
            AsyncCallback<Comment> callback);

    void getComments(SkillAssertion sa, AsyncCallback<Comment[]> callback);

    void getAssertions(SkillA skill, AsyncCallback<SkillAssertion[]> callback);

    void getAssertion(String key, AsyncCallback<SkillAssertion> callback);

}
