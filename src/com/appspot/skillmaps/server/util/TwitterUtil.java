/**
 *
 */
package com.appspot.skillmaps.server.util;

import org.slim3.datastore.Datastore;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.appspot.skillmaps.server.meta.GlobalSettingMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.GlobalSetting;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author go
 *
 */
public class TwitterUtil {

    /**
     * Twitter連携用のインスタンスを取得します
     * @return
     */
    public static Twitter getTwitterInstance(){
        Twitter twitter = new TwitterFactory().getInstance();
        GlobalSetting gs = Datastore.query(GlobalSettingMeta.get()).limit(1).asSingle();
        twitter.setOAuthConsumer(gs.getTwitterConsumerKey(), gs.getTwitterConsumerSecret());
        return twitter;
    }

    /**
     * スキルアピールをツイートします。
     * @return
     */
    @SuppressWarnings("deprecation")
    public static void tweetSkillAppeal(SkillAppeal skillAppeal){
        ProfileMeta meta = ProfileMeta.get();
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Profile profile = Datastore.query(meta).filter(meta.userEmail.equal(user.getEmail())).limit(1).asSingle();
        Twitter twitter = TwitterUtil.getTwitterInstance();

        twitter.setOAuthAccessToken(profile.getTwitterToken(), profile.getTwitterTokenSecret());
        String skillmapsUrl = "http://j.mp/dfQBqk";
        String description = skillAppeal.getDescription();
        try{
            if(description != null && description.length() > 108){
                description = skillAppeal.getDescription().substring(0, 108) + "…";
            }
            twitter.updateStatus(description + " #skillmaps " + skillmapsUrl);
        }catch(TwitterException te){
            throw new IllegalArgumentException("Oops, there must be something wrong with Twitter.");
        }
    }

    /**
     * スキルがついたことをツイートします。
     * @return
     */
    @SuppressWarnings("deprecation")
    public static void tweetSkillAppended(Skill skill){
        try{
            ProfileMeta meta = ProfileMeta.get();
            Profile skillOwner = Datastore.query(meta).filter(meta.userEmail.equal(skill.getOwnerEmail())).limit(1).asSingle();
            String skillOwnerId = skillOwner.getId();
            // なぜか取得するときにエラーになる。Twitter4Jの問題？
//            if (skillOwner.isEnabledTwitter()) {
//                Twitter skillOwnerTwitter = TwitterUtil.getTwitterInstance();
//                skillOwnerTwitter.setOAuthAccessToken(skillOwner.getTwitterToken(), skillOwner.getTwitterTokenSecret());
//                skillOwnerId = "@" + skillOwnerTwitter.getScreenName();
//            }

            UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            Profile skillAppender = Datastore.query(meta).filter(meta.userEmail.equal(user.getEmail())).limit(1).asSingle();
            String skillAppenderId = skillAppender.getId();
            // なぜか取得するときにエラーになる。Twitter4Jの問題？
//            if (skillAppender.isEnabledTwitter()) {
//                Twitter skillAppenderTwitter = TwitterUtil.getTwitterInstance();
//                skillAppenderTwitter.setOAuthAccessToken(skillAppender.getTwitterToken(), skillAppender.getTwitterTokenSecret());
//                skillAppenderId = "@" + skillAppenderTwitter.getScreenName();
//            }

            GlobalSetting gs = Datastore.query(GlobalSettingMeta.get()).limit(1).asSingle();
            Profile notifier = gs.getTwitterNotifier().getModel();
            if (!notifier.isEnabledTwitter()) {
                System.err.println("Twitter notifier not configured.");
                return;
            }
            Twitter notifierTwitter = TwitterUtil.getTwitterInstance();
            notifierTwitter.setOAuthAccessToken(notifier.getTwitterToken(), notifier.getTwitterTokenSecret());

            String skillmapsUrl = "http://skillmaps.appspot.com/user.html?id=" + skillOwner.getId();
            String body = String.format("%s さんが %s さんのスキル[%s]に賛同しました.ポイント[%d] #skillmaps %s"
                , skillAppenderId
                , skillOwnerId
                , skill.getName()
                , skill.getPoint()
                , skillmapsUrl);
            notifierTwitter.updateStatus(body);
        }catch(TwitterException te){
            System.err.println(te);
            System.err.println(te.getMessage());
            System.err.println(te.getStackTrace());
            // Tweetできなくても成功したことにする
//            throw new IllegalArgumentException("Oops, there must be something wrong with Twitter.");
        }
    }
}
