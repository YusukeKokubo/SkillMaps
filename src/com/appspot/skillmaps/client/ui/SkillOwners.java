package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class SkillOwners extends Composite {

    private static SkillOwnersUiBinder uiBinder = GWT
        .create(SkillOwnersUiBinder.class);

    private final SkillServiceAsync service = GWT.create(SkillService.class);

    @UiField
    FlexTable table;

    @UiField
    UserDialog userDialog;

    interface SkillOwnersUiBinder extends UiBinder<Widget, SkillOwners> {
    }

    public SkillOwners(final Login login, String skillName) {
        initWidget(uiBinder.createAndBindUi(this));

        service.getSkillOwners(skillName, new AsyncCallback<Skill[]>() {
            @Override
            public void onSuccess(Skill[] result) {
                table.setText(0, 0, "スキル");
                table.setText(0, 1, "賛同者");
                table.setText(0, 2, "ユーザー");
                table.setText(0, 3, "説明");
                table.getRowFormatter().addStyleName(0, "grid-columns");
                for (int i = 0; i < result.length; i ++) {
                    Skill s = result[i];
                    table.setText(i + 1, 0, s.getName());
                    table.setText(i + 1, 1, String.valueOf(s.getPoint()));
                    table.setWidget(i + 1, 2, new UserThumnail(login, s.getProfile()));
                    table.setText(i + 1, 3, s.getDescription());
                }
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }
}
