package com.appspot.skillmaps.client;

import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.client.ui.ActivateGuidance;
import com.appspot.skillmaps.client.ui.Footer;
import com.appspot.skillmaps.client.ui.Header;
import com.appspot.skillmaps.client.ui.SigninGuidance;
import com.appspot.skillmaps.client.ui.SkillAppealForm;
import com.appspot.skillmaps.client.ui.SkillAppealTimeLine;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
//import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class Home implements EntryPoint {

    AccountServiceAsync service = GWT.create(AccountService.class);

    @Override
    public void onModuleLoad() {
        service.login(GWT.getHostPageBaseURL(),
            new AsyncCallback<Login>() {
                @Override
                public void onSuccess(final Login login) {
                    RootPanel.get("header").add(new Header(login));
                    if (login.isLoggedIn()) {
                        if (login.getProfile().isActivate()) {
                            RootPanel.get("add-skillappeal").add(new SkillAppealForm(login));
                        } else {
                            RootPanel.get("add-skillappeal").add(new ActivateGuidance(login));
                        }
                    } else {
                        RootPanel.get("add-skillappeal").add(new SigninGuidance(login));
                    }
                    RootPanel.get("skill-appeals").add(new SkillAppealTimeLine(login));
                    RootPanel.get("footer").add(new Footer());
                }

                @Override
                public void onFailure(Throwable caught) {
//                    Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
                }
            });
    }

}
