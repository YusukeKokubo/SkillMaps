/**
 * 
 */
package com.appspot.skillmaps.server.util;

import org.slim3.datastore.Datastore;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author go
 *
 */
public class TwitterUtil {
    
    public final static String CONSUMER_KEY = "";
    
    public final static String CONSUMER_SECRET = "";
    
    /**
     * Twitter連携用のインスタンスを取得します
     * @return
     */
    public static Twitter getTwitterInstance(){
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        return twitter;
    }
    
    /**
     * Datastoreに保存してあるトークンからAccessTokenをロードします
     * @return
     */
    public static AccessToken loadAccessToken(){
        ProfileMeta meta = ProfileMeta.get();
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Profile profile = Datastore.query(meta).filter(meta.userEmail.equal(user.getEmail())).asSingle();
        if(profile != null){
            return new AccessToken(profile.getTwitterToken(), profile.getTwitterTokenSecret());
        }
        return null;
    }
}
