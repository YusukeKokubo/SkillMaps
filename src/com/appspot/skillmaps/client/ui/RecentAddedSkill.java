package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.service.AccountService;
import com.appspot.skillmaps.client.service.AccountServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RecentAddedSkill extends Composite {

    private static RecentAddedSkillUiBinder uiBinder = GWT
        .create(RecentAddedSkillUiBinder.class);

    AccountServiceAsync service = GWT.create(AccountService.class);
    
    interface RecentAddedSkillUiBinder extends
            UiBinder<Widget, RecentAddedSkill> {
    }
    
    @UiField
    VerticalPanel users;
    
    @UiField
    PopupPanel userDialog;

    public RecentAddedSkill(final Login login) {
        initWidget(uiBinder.createAndBindUi(this));
        
        service.getRecentEntriedUsers(new AsyncCallback<Profile[]>() {
            @Override
            public void onSuccess(Profile[] result) {
                for (Profile user : result) {
                    users.add(new UserThumnail(login, user, userDialog));
                }
            }
            
            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

}
