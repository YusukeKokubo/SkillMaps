package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.SkillOwnersDisplay;
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

    private Presenter presenter;

    private final Provider<UserThumnail> utProvider;

    interface SkillOwnersUiBinder extends UiBinder<Widget, SkillOwners> {
    }

    @Inject
    public SkillOwners(Provider<UserThumnail> utProvider) {
        this.utProvider = utProvider;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setSkills(Skill[] result){
        table.removeAllRows();

        table.setText(0, 0, "スキル");
        table.setText(0, 1, "賛同者");
        table.setText(0, 2, "ポイント");
        table.setText(0, 3, "ユーザー");
        table.setText(0, 4, "説明");
        table.getRowFormatter().addStyleName(0, "grid-columns");

        for (int i = 0; i < result.length; i ++) {
            Skill s = result[i];
            table.setText(i + 1, 0, s.getName());
            table.setText(i + 1, 1, String.valueOf(s.getAgreedCount()));
            table.setText(i + 1, 2, String.valueOf(s.getPoint()));
            UserThumnail uT = utProvider.get();
            uT.setUser(s.getProfile());
            table.setWidget(i + 1, 3, uT);
            table.setText(i + 1, 4, s.getDescription());
        }
    }

    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }
}
