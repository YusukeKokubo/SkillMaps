package com.appspot.skillmaps.server.controller;

import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.SkillRelationMeta;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillRelation;

public class PointdownController extends Controller {
    SkillRelationMeta m = SkillRelationMeta.get();
    long day = 1000 * 60 * 60 * 24;

    @Override
    public Navigation run() throws Exception {
        // 7日以上経過してるSkillポイントをデクリメントする
        Date now = new Date(new Date().getTime() - day * 7);
        List<SkillRelation> rels = Datastore.query(m).filter(m.updatedAt.lessThan(now)).asList();
        
        for (SkillRelation rel : rels) {
            if (rel.getPoint() == null) continue;
            rel.setPoint(rel.getPoint() - 1L);
            Skill skill = rel.getSkill().getModel();
            skill.calcPoint();
            if (skill.getPoint() <= 0) {
                skill.setEnable(false);
            }
            Datastore.put(rel, skill);
        }
        return null;
    }
}
