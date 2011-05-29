package com.appspot.skillmaps.server.controller.github;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.GlobalSettingMeta;
import com.appspot.skillmaps.server.meta.ProfileMeta;
import com.appspot.skillmaps.shared.model.GlobalSetting;
import com.appspot.skillmaps.shared.model.Profile;
import com.github.api.v2.services.GitHubServiceFactory;
import com.github.api.v2.services.OAuthService;
import com.github.api.v2.services.UserService;
import com.github.api.v2.services.auth.OAuthAuthentication;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class RegistrationController extends Controller {

    private static final ProfileMeta m = ProfileMeta.get();
    private static final GlobalSettingMeta gm = GlobalSettingMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        GlobalSetting setting = Datastore.query(gm).limit(1).asSingle();
        OAuthService service = 
            GitHubServiceFactory.newInstance().createOAuthService(
                setting.getGithubClientId(), setting.getGithubClientSecret());

        String code = (String) request.getAttribute("code");

        String callbackUrl = "http://skillmaps.appspot.com/github/registration";
        String accessToken = service.getAccessToken(callbackUrl, code);

        User user = UserServiceFactory.getUserService().getCurrentUser();
        Profile profile = Datastore.query(m).filter(m.userEmail.equal(user.getEmail())).asSingle();
        
        UserService s = GitHubServiceFactory.newInstance().createUserService();
        s.setAuthentication(new OAuthAuthentication(accessToken));
        
        profile.setGithubToken(accessToken);
        profile.setGithubLogin(s.getCurrentUser().getLogin());
        Datastore.put(profile);
        return redirect("/");
    }
}
