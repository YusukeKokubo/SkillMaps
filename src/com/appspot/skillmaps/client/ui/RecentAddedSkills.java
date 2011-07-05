package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.RecentAddedSkillsDisplay;
import com.appspot.skillmaps.shared.model.SkillAssertion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class RecentAddedSkills extends Composite implements RecentAddedSkillsDisplay{

    private static RecentEntriedUsersUiBinder uiBinder = GWT
        .create(RecentEntriedUsersUiBinder.class);

    interface RecentEntriedUsersUiBinder extends
            UiBinder<Widget, RecentAddedSkills> {
    }

    @UiField
    VerticalPanel skills;

    private Presenter presenter;

    private final Provider<SkillThumnail> skillThumnailProvider;

    @Inject
    public RecentAddedSkills(Provider<SkillThumnail> skillThumnailProvider) {
        this.skillThumnailProvider = skillThumnailProvider;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }

    @Override
    public void setRecentAddedSkills(SkillAssertion[] result) {
        for (SkillAssertion sa : result) {
            SkillThumnail skillThumnail = skillThumnailProvider.get();
            skillThumnail.setSkill(sa);
            skills.add(skillThumnail);
        }
    }
}
