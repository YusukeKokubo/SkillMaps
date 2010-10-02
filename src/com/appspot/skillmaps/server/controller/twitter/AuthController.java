package com.appspot.skillmaps.server.controller.twitter;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.AppEngineUtil;

import twitter4j.Twitter;
import twitter4j.http.RequestToken;

import com.appspot.skillmaps.server.util.TwitterUtil;

public class AuthController extends Controller {

    @Override
    public Navigation run() throws Exception {
        Twitter twitter = TwitterUtil.getTwitterInstance();
        
        String callbackUrl = "http://skillmaps.appspot.com/twitter/registration";
        if(AppEngineUtil.isDevelopment()){
            callbackUrl = "http://127.0.0.1:8888/twitter/registration";
        }
        RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
        sessionScope("requestToken", requestToken);
        return redirect(requestToken.getAuthorizationURL());
    }
}
