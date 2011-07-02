package com.appspot.skillmaps.client.ui.parts;

import com.appspot.skillmaps.shared.model.SkillAssertion;

public interface PartsFactory {

    public AgreeAnchor createAgreeAnchor(SkillAssertion skillAssertion, AgreeAnchor.ActionSuccessHandler handler);
}
