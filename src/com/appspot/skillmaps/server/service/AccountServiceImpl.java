package com.appspot.skillmaps.server.service;

import java.util.ArrayList;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.memcache.Memcache;
import org.slim3.util.StringUtil;

import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.server.meta.FollowingMeta;
import com.appspot.skillmaps.server.meta.IconMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.dto.UserListResultDto;
import com.appspot.skillmaps.shared.model.Following;
import com.appspot.skillmaps.shared.model.Icon;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.collect.Lists;


public class AccountServiceImpl implements AccountService {
    private static final int USER_LISE_SIZE = 20;
    FollowingMeta fm = FollowingMeta.get();
    ProfileMeta pm = ProfileMeta.get();

    @Override
    public Login login(String requestUri) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Login login = new Login();

        if (user != null) {
            login.setLoggedIn(true);
            login.setAdmin(userService.isUserAdmin());
            login.setEmailAddress(user.getEmail());
            login.setNickname(user.getNickname());
            login.setLogoutUrl(userService.createLogoutURL(requestUri));
            Profile p = Datastore.query(pm).filter(pm.userEmail.equal(user.getEmail())).limit(1).asSingle();
            if (p == null) {
                p = new Profile();
                p.setHasIcon(false);
                p.setAllowFromMailNotifier(true);
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
        }
        return login;
    }

    @Override
    public Profile[] findUsers(String id) {
        return Datastore.query(pm).filter(pm.id.startsWith(id)).asList().toArray(new Profile[0]);
    }

    @Override
    public Profile getUser(String id) {
        return Datastore.query(pm).filter(pm.id.equal(id)).limit(1).asSingle();
    }

    public Profile getUserByEmail(String email){
        Profile p = Memcache.get(email);
        if(p != null){
            return p;
        }
        p = Datastore.query(pm).filter(pm.userEmail.equal(email)).limit(1).asSingle();
        if(p != null){
            putMemcache(email, p);
        }
        return p;
    }

    public Profile[] getUsersByEmail(String[] emails) {
        List<String> nonMemcached = Lists.newArrayListWithCapacity(emails.length);
        List<Profile> profiles = Lists.newArrayListWithCapacity(emails.length);
        for (String email : nonMemcached) {
            Profile p = Memcache.get(email);
            if(p != null){
                profiles.add(p);
            } else {
                nonMemcached.add(email);
            }
        }
        if(emails.length != 0){
            List<Profile> list = Datastore.query(pm).filter(pm.userEmail.in(emails)).asList();

            for (Profile profile : list) {
                putMemcache(profile.getUserEmail(), profile);
            }
            profiles.addAll(list);
        }
        return profiles.toArray(new Profile[0]);
    }

    @Override
    public Profile[] getUsers() {
        List<Profile> result = Datastore.query(pm).filter(pm.id.isNotNull()).asList();
        return result.toArray(new Profile[0]);
    }

    @Override
    public Profile[] getRecentEntriedUsers() {
        List<Profile> result = Datastore.query(pm).sort(pm.createdAt.desc).filterInMemory(pm.id.isNotNull()).limit(25).asList();
        for (Profile profile : result) {
            putMemcache(profile.getUserEmail(), profile);
        }
        return result.toArray(new Profile[0]);
    }

    @Override
    public Profile[] getRecentEntriedUsersWithCursor(int pageNum) {

        List<Profile> result = Datastore.query(pm)
                                            .sort(pm.createdAt.desc)
                                            .filterInMemory(pm.id.isNotNull())
                                            .offset(USER_LISE_SIZE * pageNum)
                                            .limit(USER_LISE_SIZE)
                                            .asList();

        for (Profile profile : result) {
            putMemcache(profile.getUserEmail(), profile);
        }

        return result.toArray(new Profile[0]);
    }

    @Override
    public UserListResultDto getUserList(){
        return getUsers(null, null, null);
    }

    @Override
    public UserListResultDto getUsers(String encodedCursor,
                                      String encodedFilter,
                                      String encodedSorts){
        if(StringUtil.isEmpty(encodedCursor)
                || StringUtil.isEmpty(encodedFilter)
                || StringUtil.isEmpty(encodedSorts)){

            //空は初回
            S3QueryResultList<Profile> result = Datastore.query(pm)
                                                            .sort(pm.createdAt.desc)
                                                            .prefetchSize(USER_LISE_SIZE)
                                                            .offset(0)
                                                            .limit(USER_LISE_SIZE)
                                                            .asQueryResultList();


            UserListResultDto resultDto = createUserListResultDto(result);

            return resultDto;
        }
        S3QueryResultList<Profile> result = Datastore.query(pm).prefetchSize(USER_LISE_SIZE)
                                                    .encodedStartCursor(encodedCursor)
                                                    .encodedFilters(encodedFilter)
                                                    .encodedSorts(encodedSorts)
                                                    .offset(0)
                                                    .limit(USER_LISE_SIZE)
                                                    .asQueryResultList();

        UserListResultDto resultDto = createUserListResultDto(result);
        return resultDto;
    }

    @Override
    public String getSignUrl(String backUrl) {
        UserService us = UserServiceFactory.getUserService();
        return us.createLoginURL(backUrl);
    }

    @Override
    public Profile[] getFollowerTo(Profile p) {
        List<Following> following = Datastore.query(fm).filter(fm.toEmail.equal(p.getUserEmail())).asList();
        List<String> keys = new ArrayList<String>();
        for (Following f : following) {
            keys.add(f.getFromEmail());
        }
        return getUsersByEmail(keys.toArray(new String[0]));
    }

    @Override
    public Profile[] getFollowingBy(Profile p) {
        List<Following> follower = Datastore.query(fm).filter(fm.fromEmail.equal(p.getUserEmail())).asList();
        List<String> keys = new ArrayList<String>();
        for (Following f : follower) {
            keys.add(f.getToEmail());
        }

        if(keys.isEmpty()) {
            return new Profile[0];
        }

        return getUsersByEmail(keys.toArray(new String[0]));
    }

    @Override
    public Profile[] getFriends() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (userService.isUserLoggedIn() == false)
            return new Profile[0];
        Profile p = new Profile();
        p.setUserEmail(user.getEmail());
        return getFriends(p);
    }

    @Override
    public Profile[] getFriends(Profile p) {
        List<Following> following = Datastore.query(fm).filter(fm.toEmail.equal(p.getUserEmail())).asList();
        List<Following> follower = Datastore.query(fm).filter(fm.fromEmail.equal(p.getUserEmail())).asList();

        List<String> followingEmails = new ArrayList<String>();
        for (Following f : following) {
            followingEmails.add(f.getFromEmail());
        }

        List<String> keys = new ArrayList<String>();
        for (Following f : follower) {
            if (followingEmails.contains(f.getToEmail())){
                keys.add(f.getToEmail());
            }
        }
        if (keys.isEmpty()) return new Profile[0];
        return getUsersByEmail(keys.toArray(new String[0]));
    }


    private UserListResultDto createUserListResultDto(
            S3QueryResultList<Profile> result) {
        UserListResultDto resultDto = new UserListResultDto();
        List<Profile> list = Datastore.filterInMemory(result, pm.id.isNotNull());

        resultDto.setUsers(list.toArray(new Profile[0]));
        resultDto.setEncodedCursor(result.getEncodedCursor());
        resultDto.setEncodedFilter(result.getEncodedFilters());
        resultDto.setEncodedSorts(result.getEncodedSorts());
        resultDto.setHasNext(result.hasNext());
        return resultDto;
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
        putMemcache(act.getUserEmail(), act);

        Datastore.put(act);
    }

    private void putMemcache(String email, Profile p) {
        try{
            Memcache.put(email, p);
        } catch(Exception e){
        }
    }
}
