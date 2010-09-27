package com.appspot.skillmaps.server.service;

import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.server.meta.IconMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.Icon;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class AccountServiceImpl implements AccountService {
    ProfileMeta pm = ProfileMeta.get();

    @Override
    public Login login(String requestUri) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Login login = new Login();

        if (user != null) {
            login.setLoggedIn(true);
            login.setEmailAddress(user.getEmail());
            login.setNickname(user.getNickname());
            login.setLogoutUrl(userService.createLogoutURL(requestUri));
            Profile p = Datastore.query(pm).filter(pm.userEmail.equal(user.getEmail())).asSingle();
            if (p == null) {
                p = new Profile();
                Icon i = new Icon();
                p = Datastore.get(ProfileMeta.get(), Datastore.put(p));
                i = Datastore.get(IconMeta.get(), Datastore.put(i));
                i.setKeyString(KeyFactory.keyToString(i.getKey()));
                p.setIconKey(i.getKey());
                p.setIconKeyString(KeyFactory.keyToString(i.getKey()));
                p = Datastore.get(ProfileMeta.get(), Datastore.put(p));
                i = Datastore.get(IconMeta.get(), Datastore.put(i));
            }
            login.setProfile(p);
        } else {
            login.setLoggedIn(false);
            login.setLoginUrl(userService.createLoginURL(requestUri));
        }

        return login;
    }

    @Override
    public Profile getUser(String id) {
        return Datastore.query(pm).filter(pm.id.equal(id)).asSingle();
    }

    @Override
    public Profile[] getUsers() {
        List<Profile> result = Datastore.query(pm).filter(pm.id.isNotNull()).asList();
        return result.toArray(new Profile[0]);
    }

    @Override
    public void putProfile(Profile act) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalArgumentException("the user is null");

        if (StringUtil.isEmpty(act.getId())) {
            throw new IllegalArgumentException("[ID] は必ず入力してください");
        }

        if (!act.getId().matches("[\\w\\-]+")) {
            throw new IllegalArgumentException("[ID] は英数字とハイフンのみ入力可能です");
        }

        Profile p = Datastore.query(pm).filter(pm.id.equal(act.getId())).asSingle();
        if (p != null && !p.getKey().equals(act.getKey())) {
            throw new IllegalArgumentException("この [ID] は既に登録済みです");
        }

        if (StringUtil.isEmpty(act.getName())) {
            throw new IllegalArgumentException("[おなまえ] は必ず入力してください");
        }

        Datastore.put(act);
    }
}
