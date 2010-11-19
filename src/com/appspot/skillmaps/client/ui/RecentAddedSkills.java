package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.RecentAddedSkillsDisplay;
import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RecentAddedSkills extends Composite implements RecentAddedSkillsDisplay{

    private static RecentEntriedUsersUiBinder uiBinder = GWT
        .create(RecentEntriedUsersUiBinder.class);

    final SkillServiceAsync service = GWT.create(SkillService.class);

    interface RecentEntriedUsersUiBinder extends
            UiBinder<Widget, RecentAddedSkills> {
    }

    @UiField
    VerticalPanel skills;

    @UiField
    UserDialog userDialog;

    private Presenter presenter;

    @Inject
    public RecentAddedSkills(final Login login) {
        initWidget(uiBinder.createAndBindUi(this));

        service.getRecentAddedSkills(new AsyncCallback<Skill[]>() {
            @Override
            public void onSuccess(Skill[] result) {
                for (Skill skill : result) {
                    skills.add(new SkillThumnail(login, skill, userDialog));
                }
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

}
