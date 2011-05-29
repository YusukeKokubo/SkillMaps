package com.appspot.skillmaps.server.controller.github;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.appspot.skillmaps.server.meta.GlobalSettingMeta;
import com.appspot.skillmaps.shared.model.GlobalSetting;
import com.github.api.v2.services.GitHubServiceFactory;
import com.github.api.v2.services.OAuthService;

public class AuthController extends Controller {

    private static final GlobalSettingMeta gm = GlobalSettingMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        GlobalSetting setting = Datastore.query(gm).limit(1).asSingle();
        OAuthService service = 
            GitHubServiceFactory.newInstance().createOAuthService(
                setting.getGithubClientId(), setting.getGithubClientSecret());

        String callbackUrl = "http://skillmaps.appspot.com/github/registration";
        // callbackは変えられないらしい
//        if(AppEngineUtil.isDevelopment()){
//            callbackUrl = "http://127.0.0.1:8888/github/registration";
//        }
        
        String autorizationUrl = service.getAuthorizationUrl(callbackUrl);
        return redirect(autorizationUrl);
    }
}
