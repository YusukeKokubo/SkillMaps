package com.appspot.skillmaps.server.controller.sys;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.SkillRelationMeta;
import com.appspot.skillmaps.shared.model.Following;
import com.appspot.skillmaps.shared.model.SkillRelation;

public class CreateFollowingController extends Controller {

    @Override
    public Navigation run() throws Exception {
        SkillRelationMeta m = SkillRelationMeta.get();
        
        List<SkillRelation> rels = Datastore.get(m);
        for (SkillRelation rel : rels) {
            String from = rel.getProfile().getUserEmail();
            String to = rel.getSkill().getModel().getOwnerEmail();
            
            Following f = new Following();
            f.setToEmail(to);
            f.setFromEmail(from);
            
            Datastore.put(f);
        }
        return null;
    }
}
