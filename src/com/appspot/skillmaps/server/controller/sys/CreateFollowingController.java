package com.appspot.skillmaps.server.controller.sys;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.FollowingMeta;
import com.appspot.skillmaps.server.meta.SkillRelationMeta;
import com.appspot.skillmaps.shared.model.Following;
import com.appspot.skillmaps.shared.model.SkillRelation;

public class CreateFollowingController extends Controller {

    @Override
    public Navigation run() throws Exception {
        SkillRelationMeta m = SkillRelationMeta.get();
        FollowingMeta fm = FollowingMeta.get();
        List<SkillRelation> rels = Datastore.query(m).asList();
        for (SkillRelation rel : rels) {
            try {
                String from = rel.getUserEmail();
                String to = rel.getSkill().getModel().getOwnerEmail();
                
                if (Datastore.query(fm)
                        .filter(fm.toEmail.equal(to))
                        .filter(fm.fromEmail.equal(from)).count() > 0) {
                    continue;
                }
                
                Following f = new Following();
                f.setToEmail(to);
                f.setFromEmail(from);
                
                Datastore.put(f);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
