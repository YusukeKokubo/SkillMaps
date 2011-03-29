package com.appspot.skillmaps.server.controller.sys;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.Profile;

public class CleanupProfileController extends Controller {
    static ProfileMeta m = ProfileMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        // 本当はTaskQueue使ってやるのがよいけど手抜きした
        for (Profile p : Datastore.query(m).asList()) {
            p.setAllowFromMailNotifier(true);
            Datastore.put(p);
        }
        return null;
    }
}
