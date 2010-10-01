package com.appspot.skillmaps.client;

import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.client.ui.Footer;
import com.appspot.skillmaps.client.ui.Header;
import com.appspot.skillmaps.client.ui.ProfileUI;
import com.appspot.skillmaps.client.ui.SigninGuidance;
import com.appspot.skillmaps.client.ui.UserUI;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
//import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class MyPage implements EntryPoint {

    AccountServiceAsync service = GWT.create(AccountService.class);

    @Override
    public void onModuleLoad() {
        service.login(GWT.getHostPageBaseURL(),
            new AsyncCallback<Login>() {
                @Override
                public void onSuccess(Login result) {
                    RootPanel.get("header").add(new Header(result));
                    if (result.isLoggedIn()) {
                        RootPanel.get("profile").add(new ProfileUI(result));
                        RootPanel.get("user").add(new UserUI(result, result.getProfile()));
                    } else {
                        RootPanel.get("profile").add(new SigninGuidance(result));
                    }
                    RootPanel.get("footer").add(new Footer());
                }

                @Override
                public void onFailure(Throwable caught) {
//                    Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
                }
            });
    }

}
