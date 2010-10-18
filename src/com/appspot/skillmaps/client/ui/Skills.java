package com.appspot.skillmaps.client.ui;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class Skills extends Composite {

    private static SkillsUiBinder uiBinder = GWT.create(SkillsUiBinder.class);

    private final SkillServiceAsync service = GWT.create(SkillService.class);

    interface SkillsUiBinder extends UiBinder<Widget, Skills> {
    }

    @UiField
    Tree skillsPanel;

    @UiField
    PopupPanel userDialog;

    public Skills(final Login login) {
        initWidget(uiBinder.createAndBindUi(this));

        service.getPopularSkills(new AsyncCallback<HashMap<String,ArrayList<Skill>>>() {
            @Override
            public void onSuccess(HashMap<String, ArrayList<Skill>> result) {
                for (String skillname : result.keySet()) {
                    ArrayList<Skill> skillmap = result.get(skillname);
                    TreeItem item = new TreeItem();
                    FlexTable users = new FlexTable();
                    users.setText(0, 0, "ポイント");
                    users.setText(0, 1, "ユーザー");
                    users.getRowFormatter().addStyleName(0, "grid-columns");
                    long point = 0L;
                    for (int i = 0; i < skillmap.size(); i ++) {
                        Skill skill = skillmap.get(i);
                        users.setText(i + 1, 0, skill.getPoint().toString());
                        users.setWidget(i + 1, 1, new UserThumnail(login, skill.getProfile(), userDialog));
                        point += skill.getPoint();
                    }
                    item.addItem(users);
                    item.setText(skillname + "(" + point + ")");
                    skillsPanel.addItem(item);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

}
