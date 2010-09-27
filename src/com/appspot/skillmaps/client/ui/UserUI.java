package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class UserUI extends Composite {

    private static UserUiBinder uiBinder = GWT.create(UserUiBinder.class);

    private final SkillServiceAsync service = GWT.create(SkillService.class);

    interface UserUiBinder extends UiBinder<Widget, UserUI> {
    }

    @UiField
    Anchor id;

    @UiField
    Label name;

    @UiField
    Image icon;

    @UiField
    FlexTable skills;

    @UiField
    Anchor addSkill;

    @UiField
    DialogBox form;

    @UiField
    TextBox skillName;

    @UiField
    TextArea description;

    @UiField
    Button submit;

    @UiField
    Button cansel;

    public UserUI(Login login, final Profile profile) {
        initWidget(uiBinder.createAndBindUi(this));

        id.setText(profile.getId());
        name.setText(profile.getName());
        icon.setUrl("/images/icon/" + profile.getIconKeyString());

        if (!login.isLoggedIn()) {
            addSkill.setVisible(false);
        }

        addSkill.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                skillName.setText("");
                description.setText("");
                form.center();
            }
        });

        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Skill skill = new Skill();
                skill.setOwnerEmail(profile.getUserEmail());
                skill.setName(skillName.getText());
                skill.setDescription(description.getText());
                service.putSkill(skill, new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        Window.alert("追加しました");
                        reloadSkills(profile);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
                    }
                });
            }
        });

        cansel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                form.hide();
            }
        });

        reloadSkills(profile);
    }

    private void reloadSkills(final Profile profile) {
        form.hide();
        skills.clear(true);
        skills.setText(0, 0, "スキル");
        skills.setText(0, 1, "賛同者");
        skills.setText(0, 2, "説明");
        skills.getRowFormatter().addStyleName(0, "grid-columns");
        service.getSkills(profile.getUserEmail(), new AsyncCallback<Skill[]>() {
            @Override
            public void onSuccess(Skill[] result) {
                for (int i = 0; i < result.length; i ++) {
                    System.out.println(i);
                    final Skill skill = result[i];
                    skills.setText(i + 1, 0, skill.getName());
                    skills.setText(i + 1, 1, skill.getPoint().toString());
                    skills.setText(i + 1, 2, skill.getDescription());
                    skills.setWidget(i + 1, 3, new Button("自分も賛同する", new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            service.putSkill(skill, new AsyncCallback<Void>() {
                                @Override
                                public void onSuccess(Void result) {
                                    Window.alert("更新しました");
                                    reloadSkills(profile);
                                }

                                @Override
                                public void onFailure(Throwable caught) {
                                    Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
                                }
                            });
                        }
                    }));
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
            }
        });
    }
}
