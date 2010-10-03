package com.appspot.skillmaps.server.controller.twitter;

import javax.servlet.http.HttpServletRequest;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.RequestLocator;

import twitter4j.Twitter;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.server.util.TwitterUtil;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class RegistrationController extends Controller {

    ProfileMeta meta = ProfileMeta.get();

    @Override
    public Navigation run() throws Exception {
        HttpServletRequest req = RequestLocator.get();
        String oauthVerifier = (String) req.getAttribute("oauth_verifier");

        Twitter twitter = TwitterUtil.getTwitterInstance();
        RequestToken requestToken = sessionScope("requestToken");
        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);

        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Profile profile = Datastore.query(meta).filter(meta.userEmail.equal(user.getEmail())).asSingle();
        profile.setTwitterToken(accessToken.getToken());
        profile.setTwitterTokenSecret(accessToken.getTokenSecret());
        profile.setTwitterScreenName(twitter.getScreenName());
        Datastore.put(profile);

        return redirect("/mypage.html");
    }
}
