package com.appspot.skillmaps.server.service;

import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.client.service.AdminService;
import com.appspot.skillmaps.server.meta.GlobalSettingMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.GlobalSetting;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AdminServiceImpl implements AdminService {

    @Override
    public GlobalSetting getGlobalSetting() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");
        if (!userService.isUserAdmin()) throw new IllegalArgumentException("the user is not admin.");

        GlobalSetting gs = Datastore.query(GlobalSettingMeta.get()).limit(1).asSingle();
        if (gs == null) {
            return null;
        }
        gs.getTwitterNotifier().getModel();
        return gs;
    }

    @Override
    public void putGlobalSetting(GlobalSetting gs, String notifierId) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");
        if (!userService.isUserAdmin()) throw new IllegalArgumentException("the user is not admin.");

        ProfileMeta m = ProfileMeta.get();
        Profile notifier = Datastore.query(m).filter(m.id.equal(notifierId)).limit(1).asSingle();
        gs.getTwitterNotifier().setModel(notifier);
        Datastore.put(gs);
    }
}
