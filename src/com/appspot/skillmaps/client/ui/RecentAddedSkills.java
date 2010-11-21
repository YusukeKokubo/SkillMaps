package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.RecentAddedSkillsDisplay;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RecentAddedSkills extends Composite implements RecentAddedSkillsDisplay{

    private static RecentEntriedUsersUiBinder uiBinder = GWT
        .create(RecentEntriedUsersUiBinder.class);

    interface RecentEntriedUsersUiBinder extends
            UiBinder<Widget, RecentAddedSkills> {
    }

    @UiField
    VerticalPanel skills;

    @UiField
    UserDialog userDialog;

    private Presenter presenter;

    private final Login login;

    @Inject
    public RecentAddedSkills(Login login) {
        this.login = login;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setRecentAddedSkills(Skill[] result){
        for (Skill skill : result) {
            //TODO SkillThumnailにもPresenterを作成してPresenterをセット、初期化させる。
            skills.add(new SkillThumnail(login, skill));
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

}
