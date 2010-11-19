package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.SkillAppealTimeLineDisplay;
import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SkillAppealTimeLine extends Composite implements SkillAppealTimeLineDisplay{

    private static SkillAppealTimeLineUiBinder uiBinder = GWT
        .create(SkillAppealTimeLineUiBinder.class);

    private final SkillServiceAsync service = GWT.create(SkillService.class);

    interface SkillAppealTimeLineUiBinder extends
            UiBinder<Widget, SkillAppealTimeLine> {
    }

    @UiField
    VerticalPanel appealsPanel;

    @UiField
    UserDialog userDialog;

    Login login;

    private Presenter presenter;

    @Inject
    public SkillAppealTimeLine(final Login login) {
        this.login = login;
        initWidget(uiBinder.createAndBindUi(this));

        service.getSkillAppeals(new AsyncCallback<SkillAppeal[]>() {

            @Override
            public void onSuccess(SkillAppeal[] as) {
                for (SkillAppeal appeal : as) {
                    SkillAppealUI w = new SkillAppealUI(login, appeal, userDialog);
                    appealsPanel.add(w);
                    appealsPanel.setCellWidth(w, "400px");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
//                Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
            }
        });
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }
}
