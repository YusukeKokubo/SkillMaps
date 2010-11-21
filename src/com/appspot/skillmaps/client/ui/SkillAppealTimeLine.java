package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.SkillAppealTimeLineDisplay;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SkillAppealTimeLine extends Composite implements
        SkillAppealTimeLineDisplay {

    private static SkillAppealTimeLineUiBinder uiBinder =
        GWT.create(SkillAppealTimeLineUiBinder.class);

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
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setSkillAppeals(SkillAppeal[] as) {
        for (SkillAppeal appeal : as) {
            SkillAppealUI w = new SkillAppealUI(login, appeal, userDialog);
            appealsPanel.add(w);
            appealsPanel.setCellWidth(w, "400px");
        }
    }
}
