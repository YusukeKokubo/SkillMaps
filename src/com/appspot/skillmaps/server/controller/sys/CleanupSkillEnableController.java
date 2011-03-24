package com.appspot.skillmaps.server.controller.sys;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.shared.model.Skill;

public class CleanupSkillEnableController extends Controller {
    static SkillMeta m = SkillMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        // 本当はTaskQueue使ってやるのがよいけど手抜きした
        for (Skill skill : Datastore.query(m).asList()) {
            skill.setEnable(true);
            Datastore.put(skill);
        }
        return null;
    }
}
