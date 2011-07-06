package com.appspot.skillmaps.client;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.client.ui.ContentsPanel;
import com.appspot.skillmaps.client.ui.Footer;
import com.appspot.skillmaps.client.ui.Header;
import com.appspot.skillmaps.client.ui.SigninGuidance;
import com.appspot.skillmaps.client.ui.SkillMapPopupPanel;
import com.appspot.skillmaps.client.ui.UserUI;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class Skillmaps implements EntryPoint {

    @Override
    public void onModuleLoad() {
        Resources.INSTANCE.style().ensureInjected();
        final Injector injector = GWT.create(Injector.class);

        injector.getActivityManager();
        RootPanel.get("loader").setVisible(false);
        RootPanel.get("header").add(new Header(null));

        final ContentsPanel contentsPanel = injector.getContentsPanel();
        contentsPanel.init();
        RootPanel.get("contents").add(contentsPanel);

        RootPanel.get("footer").add(new Footer());
        injector.getAccountService().login(GWT.getHostPageBaseURL() + Window.Location.getQueryString(),
            new AsyncCallback<Login>() {

                @Override
                public void onSuccess(Login result) {
                    Login login = injector.getLogin();
                    login.setAdmin(result.isAdmin());
                    login.setEmail(result.getEmail());
                    login.setKey(result.getKey());
                    login.setLoggedIn(result.isLoggedIn());
                    login.setLogoutUrl(result.getLogoutUrl());
                    login.setNickname(result.getNickname());
                    login.setProfile(result.getProfile());
                    login.setVersion(result.getVersion());

                    //TODO headerもdisplay化？
                    RootPanel.get("header").clear();
                    RootPanel.get("header").add(new Header(login));

                    SkillMapPopupPanel userDialog = new SkillMapPopupPanel();
                    if (!login.isLoggedIn()) {
                        userDialog.setContents(new SigninGuidance(result));
                    } else if (!login.getProfile().isActivate()) {
                        userDialog.setContents(injector.getActivateGuidance());
                    } else {
                        UserUI ut = injector.getUserUI();
                        ut.setProfile(login.getProfile());
                        userDialog.setContents(ut);
                    }
                    userDialog.center();

                    injector.getHistoryHandler().handleCurrentHistory();
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("初期処理に失敗しました。再表示してください。");
                }
        });
    }
}
