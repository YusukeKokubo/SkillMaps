package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
//import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SkillAppealTimeLine extends Composite {

    private static SkillAppealTimeLineUiBinder uiBinder = GWT
        .create(SkillAppealTimeLineUiBinder.class);

    private final SkillServiceAsync service = GWT.create(SkillService.class);

    interface SkillAppealTimeLineUiBinder extends
            UiBinder<Widget, SkillAppealTimeLine> {
    }

    @UiField
    VerticalPanel appealsPanel;

    public SkillAppealTimeLine(final Login login) {
        initWidget(uiBinder.createAndBindUi(this));

        service.getSkillAppeals(new AsyncCallback<SkillAppeal[]>() {

            @Override
            public void onSuccess(SkillAppeal[] as) {
                for (SkillAppeal appeal : as) {
                    Profile user = appeal.getProfile();
                    HorizontalPanel panel = new HorizontalPanel();
                    panel.add(new UserThumnail(login, user));
                    panel.add(new SkillAppealUI(appeal));

                    appealsPanel.add(panel);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
//                Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
            }
        });
    }

}
