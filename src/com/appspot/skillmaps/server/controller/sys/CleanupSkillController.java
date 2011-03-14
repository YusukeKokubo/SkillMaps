package com.appspot.skillmaps.server.controller.sys;

import java.util.Date;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.SkillMeta;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillRelation;


public class CleanupSkillController extends Controller {
    static SkillMeta m = SkillMeta.get();
    long day = 1000 * 60 * 60 * 24;
    
    @Override
    public Navigation run() throws Exception {
        // 本当はTaskQueue使ってやるのがよいけど手抜きした
        Date now = new Date(new Date().getTime() - day * 1);
        for (Skill skill : Datastore.query(m).filter(m.updatedAt.lessThan(now)).asList()) {
            for (SkillRelation rel : skill.getRelation().getModelList()) {
                rel.setPoint(10L);
            }
            Datastore.put(skill.getRelation().getModelList().toArray());
            skill.calcPoint();
            skill.setAgreedCount((long)skill.getRelation().getModelList().size());
            Datastore.put(skill); // 本当は150件ずつまとめてコミットした方が良いと思うけど手抜きした
        }
        return null;
    }
}
