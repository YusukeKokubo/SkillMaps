package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class UserUI extends Composite implements UserUIDisplay{

    private static UserUiBinder uiBinder = GWT.create(UserUiBinder.class);

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

    FlexTable skills;

    @UiField
    Anchor addSkill;

    Login login;

    Profile profile;

    private Presenter presenter;

    @UiField
    SimplePanel skillsPanel;

    private final Anchor appealAnchor;

    private final Provider<UserThumnail> utProvider;

    @Override
    public void setProfile(Profile profile){
        presenter.setProfile(profile);
        this.profile = profile;
        id.setText(profile.getId());
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
        presenter.reloadSkills();
    }

    @UiHandler("id")
    public void clickId(ClickEvent e){
        presenter.gotoUser(profile.getId());
    }

    @UiHandler("addSkill")
    public void clickAddSkill(ClickEvent e){
        presenter.showSkillAddDialog();
    }

    @Inject
    public UserUI(Login login ,
                  Provider<UserThumnail> utProvider,
                  @Named("appealAnchor") Anchor appealAnchor) {
        this.login = login;
        this.utProvider = utProvider;
        this.appealAnchor = appealAnchor;
        initSkillTable();
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void reloadSkills(Skill[] skillList) {
        if(skills == null){
            initSkillTable();
        }else {

            for(int i = 1; i < skills.getRowCount();i++){
                skills.removeRow(i);
            }
        }
        if (skillList.length <= 0) {
            skills.clear(true);
            skills.setText(0, 0, "スキルはまだありません.");
            if (login.isLoggedIn()
                    && login.getProfile().getUserEmail().equals(profile.getUserEmail())) {
                skills.setWidget(0, 1, appealAnchor);
                skills.setText(0, 2, "");
            }

            skillsPanel.setWidget(skills);

            return;
        }

        for (int i = 0; i < skillList.length; i++) {
            final int j = i + 1;
            final Skill skill = skillList[i];
            Anchor name = new Anchor(skill.getName());

            name.setStyleName("class='anchor'");

            name.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    presenter.showSkillOwnersPopup(skill);
                }
            });

            skills.setWidget(j, 0, name);

            skills.setText(j, 1, skill.getPoint().toString());

            skills.setText(j, 2, skill.getDescription());

            presenter.getSkillRelations(skill , new AsyncCallback<SkillRelation[]>() {
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
        skillsPanel.setWidget(skills);
    }

    private void initSkillTable() {
        skills = new FlexTable();
        skills.addStyleName("skill-grid");
        skills.setText(0, 0, "スキル");
        skills.setText(0, 1, "賛同者");
        skills.setText(0, 2, "説明");
        skills.getCellFormatter().addStyleName(0, 0, "skill-name");
        skills.getCellFormatter().addStyleName(0, 1, "skill-point");
        skills.getCellFormatter().addStyleName(0, 2, "skill-description");
        skills.getCellFormatter().addStyleName(0, 3, "skill-agree-link");
        skills.getCellFormatter().addStyleName(0, 4, "skill-detail");
        skills.getRowFormatter().addStyleName(0, "grid-columns");
    }

    private void makeAgrees(final Skill skill, SkillRelation[] rs, int j) {
        VerticalPanel panel = new VerticalPanel();
        for (int i = 0; i < rs.length; i ++) {
            SkillRelation sr = rs[i];
            Profile p = sr.getProfile();
            UserThumnail userThumnail = utProvider.get();
            userThumnail.setUser(p);
            panel.add(userThumnail);;
            Label agreeComment = new Label(sr.getComment());
            agreeComment.addStyleName("agree-comment");
            panel.add(agreeComment);
        }
        skills.setWidget(j, 4, panel);
    }

    private Widget makeAgreedButton(final Skill skill, SkillRelation[] rs) {

        if (!login.isLoggedIn()
                || !login.getProfile().isActivate()
                || login.getEmailAddress().equals(profile.getUserEmail())) {
            return null;
        }

        for (SkillRelation rel : rs) {

            if (rel.getUserEmail().equals(login.getEmailAddress())) {
                Label lbl = new Label("賛同済み");

                return lbl;
            }
        }

        return new Button("自分も賛同する", new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                presenter.showAgreedDialog(skill);
            }
        });
    }

    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);

    }

    @Override
    public SimplePanel getSkillsPanel() {
        return skillsPanel;
    }
}
