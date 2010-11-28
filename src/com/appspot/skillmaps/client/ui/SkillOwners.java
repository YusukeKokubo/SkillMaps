package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.SkillOwnersDisplay;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillOwners extends Composite implements SkillOwnersDisplay{

    private static SkillOwnersUiBinder uiBinder = GWT
        .create(SkillOwnersUiBinder.class);

    @UiField
    FlexTable table;

    @UiField
    UserDialog userDialog;

    private Presenter presenter;

    private final Login login;

    private final Provider<UserThumnail> utProvider;

    interface SkillOwnersUiBinder extends UiBinder<Widget, SkillOwners> {
    }

    @Inject
    public SkillOwners(Login login,
                       Provider<UserThumnail> utProvider) {
        this.login = login;
        this.utProvider = utProvider;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setSkills(Skill[] result){

        table.removeAllRows();

        table.setText(0, 0, "スキル");
        table.setText(0, 1, "賛同者");
        table.setText(0, 2, "ユーザー");
        table.setText(0, 3, "説明");
        table.getRowFormatter().addStyleName(0, "grid-columns");

        for (int i = 0; i < result.length; i ++) {
            Skill s = result[i];
            table.setText(i + 1, 0, s.getName());
            table.setText(i + 1, 1, String.valueOf(s.getPoint()));
            UserThumnail uT = utProvider.get();
            uT.setUser(s.getProfile());
            table.setWidget(i + 1, 2, uT);
            table.setText(i + 1, 3, s.getDescription());
        }

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }
}
