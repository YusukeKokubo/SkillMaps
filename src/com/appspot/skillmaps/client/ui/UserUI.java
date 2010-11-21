package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
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
    Anchor twitterLink;

    @UiField
    Label selfIntroduction;

    @UiField
    Anchor profileUrl1;

    @UiField
    Anchor profileUrl2;

    @UiField
    Image icon;

    @UiField
    FlexTable skills;

    @UiField
    Anchor addSkill;

    @UiField
    DialogBox form;

    @UiField(provided=true)
    SuggestBox skillName;

    MultiWordSuggestOracle skillNames = new MultiWordSuggestOracle();

    @UiField
    TextArea description;

    @UiField
    TextArea comment;

    @UiField
    Button submit;

    @UiField
    Button cancel;

    @UiField
    UserDialog userDialog;

    @UiField
    PopupPanel appealDialog;

    @UiField
    DialogBox agreedForm;

    Login login;
    Profile profile;

    public UserUI(final Login login, final Profile profile) {
        this.skillName = new SuggestBox(skillNames);
        initWidget(uiBinder.createAndBindUi(this));
        this.login = login;
        this.profile = profile;

        id.setText(profile.getId());
        id.setHref("/user.html?id=" + profile.getId());
        name.setText(profile.getName());
        if(profile.getHasIcon() != null && profile.getHasIcon()){
            icon.setUrl("/images/icon/" + profile.getIconKeyString());
        } else {
            icon.setResource(Resources.INSTANCE.noimage150());
        }
        selfIntroduction.setText(profile.getSelfIntroduction());
        profileUrl1.setHref(profile.getProfileUrl1());
        profileUrl1.setText(profile.getProfileUrl1());
        profileUrl2.setHref(profile.getProfileUrl2());
        profileUrl2.setText(profile.getProfileUrl2());

        if (profile.isEnabledTwitter()) {
            twitterLink.setText("Twitter : @" + profile.getTwitterScreenName());
            twitterLink.setHref("http://twitter.com/" + profile.getTwitterScreenName());
        }

        if (!login.isLoggedIn() || login.getProfile().getId() == null || login.getEmailAddress().equals(profile.getUserEmail())) {
            addSkill.setVisible(false);
        }

        addSkill.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.getSkillNames(new AsyncCallback<SkillMap[]>() {

                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(SkillMap[] result) {
                        if(result == null || result.length == 0){
                            return;
                        }
                        skillNames.clear();
                        for (SkillMap skillMap : result) {
                            skillNames.add(skillMap.getSkillName());
                        }
                    }
                });
                skillName.setText("");
                description.setText("");
                comment.setText("");
                form.center();
            }
        });

        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                submit.setEnabled(false);
                Skill skill = new Skill();
                skill.setOwnerEmail(profile.getUserEmail());
                skill.setName(skillName.getText());
                skill.setDescription(description.getText());
                SkillRelation rel = new SkillRelation();
                rel.setComment(comment.getText());
                service.putSkill(skill, rel, true, new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        Window.alert("追加しました");
                        submit.setEnabled(true);
                        reloadSkills();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
                        submit.setEnabled(true);
                    }
                });
            }
        });

        cancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                form.hide();
            }
        });

        reloadSkills();
    }

    private void reloadSkills() {
        form.hide();
        agreedForm.hide();
        skills.clear(true);
        skills.setText(0, 0, "スキル");
        skills.setText(0, 1, "賛同者");
        skills.setText(0, 2, "説明");
        skills.getCellFormatter().addStyleName(0, 0, "skill-name");
        skills.getCellFormatter().addStyleName(0, 1, "skill-point");
        skills.getCellFormatter().addStyleName(0, 2, "skill-description");
        skills.getCellFormatter().addStyleName(0, 3, "skill-agree-link");
        skills.getCellFormatter().addStyleName(0, 4, "skill-detail");
        skills.getRowFormatter().addStyleName(0, "grid-columns");
        service.getSkills(profile.getUserEmail(), new AsyncCallback<Skill[]>() {
            @Override
            public void onSuccess(Skill[] result) {
                if (result.length <= 0) {
                    skills.clear(true);
                    skills.setText(0, 0, "スキルはまだありません.");
                    if (login.isLoggedIn() && login.getProfile().getUserEmail().equals(profile.getUserEmail())) {
                        Anchor link = new Anchor("アピールする");
                        link.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                VerticalPanel panel = new VerticalPanel();
                                Anchor close = new Anchor("close");
                                close.addClickHandler(new ClickHandler() {
                                    @Override
                                    public void onClick(ClickEvent event) {
                                        appealDialog.hide();
                                    }
                                });
                                panel.add(close);
                                panel.add(new SkillAppealForm(login));
                                appealDialog.setWidget(panel);
                                appealDialog.center();
                            }
                        });
                        skills.setWidget(0, 1, link);
                        skills.setText(0, 2, "");
                    }
                    return;
                }
                for (int i = 0; i < result.length; i ++) {
                    final int j = i + 1;
                    final Skill skill = result[i];
                    Anchor name = new Anchor(skill.getName());
                    name.setStyleName("class='anchor'");
                    name.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            final Anchor close = new Anchor("close");
                            close.addClickHandler(new ClickHandler() {
                                @Override
                                public void onClick(ClickEvent event) {
                                    userDialog.hide();
                                }
                            });
                            service.getSkillOwners(skill, new AsyncCallback<Skill[]>() {
                                @Override
                                public void onSuccess(Skill[] result) {
                                    VerticalPanel panel = new VerticalPanel();
                                    panel.add(close);

                                    FlexTable table = new FlexTable();
                                    table.setText(0, 0, "スキル");
                                    table.setText(0, 1, "賛同者");
                                    table.setText(0, 2, "ユーザー");
                                    table.getRowFormatter().addStyleName(0, "grid-columns");
                                    for (int i = 0; i < result.length; i ++) {
                                        Skill s = result[i];
                                        table.setText(i + 1, 0, s.getName());
                                        table.setText(i + 1, 1, String.valueOf(s.getPoint()));
                                        table.setWidget(i + 1, 2, new UserThumnail(login, s.getProfile()));
                                    }
                                    panel.add(table);
                                    panel.add(new Anchor("permalink", "/skill.html?name=" + URL.encodeComponent(skill.getName())));
                                    userDialog.setWidget(panel);
                                    userDialog.center();
                                }

                                @Override
                                public void onFailure(Throwable caught) {
                                }
                            });
                        }
                    });
                    skills.setWidget(j, 0, name);
                    skills.setText(j, 1, skill.getPoint().toString());
                    skills.setText(j, 2, skill.getDescription());

                    service.getSkillRelations(skill, new AsyncCallback<SkillRelation[]>() {
                        @Override
                        public void onSuccess(final SkillRelation[] rs) {
                            skills.setWidget(j, 3, makeAgreedButton(skill, rs));
                            final Anchor showComment = new Anchor("詳細");
                            skills.setWidget(j, 4, showComment);
                            showComment.addClickHandler(new ClickHandler() {
                                @Override
                                public void onClick(ClickEvent event) {
                                     makeAgrees(skill, rs, j);
                                     showComment.setVisible(false);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Throwable caught) {
                            skills.setText(j, 3, "データを取得できませんでした");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                skills.setText(1, 0, "データを取得できませんでした");
            }
        });
    }

    private void makeAgrees(final Skill skill, SkillRelation[] rs, int j) {
        VerticalPanel panel = new VerticalPanel();
        for (int i = 0; i < rs.length; i ++) {
            SkillRelation sr = rs[i];
            Profile p = sr.getProfile();
            panel.add(new UserThumnail(login, p));
            Label agreeComment = new Label(sr.getComment());
            agreeComment.addStyleName("agree-comment");
            panel.add(agreeComment);
        }
        skills.setWidget(j, 4, panel);
    }

    private Widget makeAgreedButton(final Skill skill, SkillRelation[] rs) {
        if (!login.isLoggedIn() || !login.getProfile().isActivate() || login.getEmailAddress().equals(profile.getUserEmail())) {
            return null;
        }
        for (SkillRelation rel : rs) {
            if (rel.getUserEmail().equals(login.getEmailAddress())) {
                Label lbl = new Label("賛同済み");
                return lbl;
            }
        }
        return new Button("自分も賛同する", new AgreedForm(skill));
    }

    private final class AgreedForm implements ClickHandler {
        private final Skill skill;
        VerticalPanel panel = new VerticalPanel();
        TextArea comment = new TextArea();
        Button agreedSubmit = new Button("submit");
        Button agreedCancel = new Button("cansel");

        private AgreedForm(Skill skill) {
            this.skill = skill;
            HorizontalPanel buttonPanel = new HorizontalPanel();
            buttonPanel.add(agreedSubmit);
            buttonPanel.add(agreedCancel);
            panel.add(new Label("コメント"));
            panel.add(comment);
            panel.add(buttonPanel);
        }

        @Override
        public void onClick(ClickEvent event) {
            agreedForm.clear();
            agreedForm.add(panel);
            agreedForm.center();
            comment.setText("");
            agreedSubmit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    agreedSubmit.setEnabled(false);
                    SkillRelation rel = new SkillRelation();
                    rel.setComment(comment.getText());
                    service.putSkill(skill, rel, true, new AsyncCallback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            Window.alert("更新しました");
                            agreedSubmit.setEnabled(true);
                            reloadSkills();
                        }

                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
                            agreedSubmit.setEnabled(true);
                        }
                    });
                }
            });
            agreedCancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    agreedForm.hide();
                }
            });
        }
    }
}
