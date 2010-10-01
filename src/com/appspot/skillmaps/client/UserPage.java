package com.appspot.skillmaps.client;

import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.client.ui.Footer;
import com.appspot.skillmaps.client.ui.Header;
import com.appspot.skillmaps.client.ui.UserUI;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class UserPage implements EntryPoint {

    AccountServiceAsync service = GWT.create(AccountService.class);

    @Override
    public void onModuleLoad() {
        service.login(GWT.getHostPageBaseURL(),
            new AsyncCallback<Login>() {
                @Override
                public void onSuccess(final Login login) {
                    RootPanel.get("header").add(new Header(login));
                    String userid = Window.Location.getParameter("id");
                    service.getUser(userid, new AsyncCallback<Profile>() {
                        @Override
                        public void onSuccess(Profile profile) {
                            RootPanel.get("user").add(new UserUI(login, profile));
                        }

                        @Override
                        public void onFailure(Throwable caught) {
//                            Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
                        }
                    });
                    RootPanel.get("footer").add(new Footer());
                }

                @Override
                public void onFailure(Throwable caught) {
//                    Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
                }
            });
    }

}
