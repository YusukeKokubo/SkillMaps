package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;

public class SigninAnchor extends Anchor {

    private final AccountServiceAsync service = GWT.create(AccountService.class);

    public SigninAnchor() {
        setHref("#");
        addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.getSignUrl(GWT.getHostPageBaseURL() + Window.Location.getQueryString() + Window.Location.getHash(),
                    new AsyncCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Window.Location.replace(result);
                        }
                        
                        @Override
                        public void onFailure(Throwable caught) {
                        }
                    });
            }
        });
    }
}
