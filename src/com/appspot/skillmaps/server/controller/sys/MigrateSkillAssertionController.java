package com.appspot.skillmaps.server.controller.sys;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.SkillAssertionMeta;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.appengine.api.datastore.KeyFactory;

public class MigrateSkillAssertionController extends Controller {
    static SkillAssertionMeta m = SkillAssertionMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        // 本当はTaskQueue使ってやるのがよいけど手抜きした
        for (SkillAssertion p : Datastore.query(m).asList()) {
            p.setKeyAsString(KeyFactory.keyToString(p.getKey()));
            Datastore.put(p);
        }
        return null;
    }
}
