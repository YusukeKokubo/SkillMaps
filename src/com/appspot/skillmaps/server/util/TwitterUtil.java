/**
 *
 *
 */
package com.appspot.skillmaps.server.util;

import java.util.regex.Pattern;

import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

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
    static final TwitterFactory factory = new TwitterFactory();

    /**
     * Twitter連携用のインスタンスを取得します
     * 
     * @return
     */
    public static Twitter getTwitterInstance() {
        Twitter twitter = factory.getInstance();
        GlobalSetting gs =
            Datastore.query(GlobalSettingMeta.get()).limit(1).asSingle();
        twitter.setOAuthConsumer(
            gs.getTwitterConsumerKey(),
            gs.getTwitterConsumerSecret());
        return twitter;
    }

    /**
     * Datastoreに保存してあるトークンからAccessTokenをロードします スキルアピールをツイートします。
     * 
     * @return
     */
    public static void tweetSkillAppeal(SkillAppeal skillAppeal) {
        ProfileMeta meta = ProfileMeta.get();
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Profile profile =
            Datastore
                .query(meta)
                .filter(meta.userEmail.equal(user.getEmail()))
                .limit(1)
                .asSingle();
        if (!profile.isEnabledTwitter()) {
            return;
        }
        Twitter twitter = TwitterUtil.getTwitterInstance();

        twitter.setOAuthAccessToken(new AccessToken(
            profile.getTwitterToken(),
            profile.getTwitterTokenSecret()));
        String skillmapsUrl = "http://j.mp/dfQBqk";
        String body =
            skillAppeal.getAppealSkillName()
                + ":"
                + skillAppeal.getDescription();
        try {
            if (body != null && body.length() > 108) {
                body = body.substring(0, 108) + "…";
            }
            twitter.updateStatus(body + " #skillmaps " + skillmapsUrl);
        } catch (TwitterException te) {
            throw new IllegalArgumentException(
                "Oops, there must be something wrong with Twitter.");
        }
    }

    /**
     * スキルがついたことをツイートします。
     * 
     * @return
     */
    public static void tweetSkillAppended(Skill skill) {
        try {
            ProfileMeta meta = ProfileMeta.get();
            Profile skillOwner =
                Datastore
                    .query(meta)
                    .filter(meta.userEmail.equal(skill.getOwnerEmail()))
                    .limit(1)
                    .asSingle();
            String skillOwnerId = skillOwner.getId();
            if (skillOwner.isEnabledTwitter()
                && !StringUtil.isEmpty(skillOwner.getTwitterScreenName())
                && skillOwner.getAllowFromTwitterNotifier() != null
                && skillOwner.getAllowFromTwitterNotifier()) {
                skillOwnerId = "@" + skillOwner.getTwitterScreenName();
            }

            UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            Profile skillAppender =
                Datastore
                    .query(meta)
                    .filter(meta.userEmail.equal(user.getEmail()))
                    .limit(1)
                    .asSingle();
            String skillAppenderId = skillAppender.getId();
            if (skillAppender.isEnabledTwitter()
                && !StringUtil.isEmpty(skillAppender.getTwitterScreenName())
                && skillAppender.getAllowFromTwitterNotifier() != null
                && skillAppender.getAllowFromTwitterNotifier()) {
                skillAppenderId = "@" + skillAppender.getTwitterScreenName();
            }

            GlobalSetting gs =
                Datastore.query(GlobalSettingMeta.get()).limit(1).asSingle();
            if (gs == null) {
                System.err.println("GlobalSetting not configured.");
                return;
            }
            Profile notifier = gs.getTwitterNotifier().getModel();
            if (notifier == null || !notifier.isEnabledTwitter()) {
                System.err.println("Twitter notifier not configured.");
                return;
            }
            Twitter notifierTwitter = TwitterUtil.getTwitterInstance();
            notifierTwitter.setOAuthAccessToken(new AccessToken(notifier
                .getTwitterToken(), notifier.getTwitterTokenSecret()));

            String skillmapsUrl =
                "http://skillmaps.appspot.com/index.html#!user:"
                    + skillOwner.getId();
            String body =
                String.format(
                    "RT %s: %s は[%s]のスキル持ってるよね.賛同者[%d] ポイント[%d] #skillmaps %s",
                    skillAppenderId,
                    skillOwnerId,
                    skill.getName(),
                    skill.getAgreedCount(),
                    skill.getPoint(),
                    skillmapsUrl);
            notifierTwitter.updateStatus(body);
        } catch (TwitterException te) {
            System.err.println(te);
            System.err.println(te.getMessage());
            System.err.println(te.getStackTrace());
            // Tweetできなくても成功したことにする
            // throw new
            // IllegalArgumentException("Oops, there must be something wrong with Twitter.");
        }
    }

    public static String getStatus(long statusId) {
        Twitter twitter = factory.getInstance();
        // twitter.getUserListStatuses(arg0, arg1, arg2)
        Paging paging = new Paging(statusId);
        try {
            return twitter.showStatus(statusId).getText();
        } catch (TwitterException te) {
            System.err.println(te);
            System.err.println(te.getMessage());
            System.err.println(te.getStackTrace());
        }
        return null;
    }

    public static boolean isTwitterTimeline(String url) {
        Pattern pattern =
            java.util.regex.Pattern
                .compile("https?://twitter\\.com/#!/.*/status/\\d*");
        return pattern.matcher(url).matches();
    }

    public static Long getTimelineId(String url) {
        if (!isTwitterTimeline(url)) {
            return null;
        }
        String[] splits = url.split("/");
        return Long.parseLong(splits[splits.length - 1]);
    }
}
