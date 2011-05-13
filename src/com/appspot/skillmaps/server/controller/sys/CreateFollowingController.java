package com.appspot.skillmaps.server.controller.sys;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.server.meta.SkillRelationMeta;
import com.appspot.skillmaps.shared.model.Following;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.SkillRelation;

public class CreateFollowingController extends Controller {

    @Override
    public Navigation run() throws Exception {
        SkillRelationMeta m = SkillRelationMeta.get();
        ProfileMeta pm = ProfileMeta.get();
        
        List<SkillRelation> rels = Datastore.get(m);
        
        for (SkillRelation rel : rels) {
            Profile follower = Datastore.query(pm).limit(1).asSingle();
            Profile following = rel.getSkill().getModel().getProfile();
            
            Following f = new Following();
            f.setFollowing(following.getKey());
            f.setFollower(follower.getKey());
            
            Datastore.put(f);
        }
        return null;
    }
}
