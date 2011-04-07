package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserUI extends Composite implements UserUIDisplay{

    private static UserUiBinder uiBinder = GWT.create(UserUiBinder.class);

    interface UserUiBinder extends UiBinder<Widget, UserUI> {
    }

    @UiField
    Label id;

    @UiField
    Label name;

    @UiField
    Anchor profileLink;

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

    FlexTable disableSkills;

    @UiField
    Anchor addSkill;

    @UiField
    DisclosurePanel disableSkillPanel;

    @UiField
    HTMLPanel disableSkillsBox;

    Login login;

    Profile profile;

    private Presenter presenter;

    @UiField
    SimplePanel skillsPanel;

    private final Provider<UserThumnail> utProvider;

    private final Injector injector;

    @Inject
    public UserUI(Login login ,
                  Provider<UserThumnail> utProvider,
                  Injector injector) {
        this.login = login;
        this.utProvider = utProvider;
        this.injector = injector;
        skills = new FlexTable();
        initSkillTable(skills);
        initWidget(uiBinder.createAndBindUi(this));
    }

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
        icon.getElement().setAttribute("align", "top");
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

    @UiHandler("profileLink")
    public void clickId(ClickEvent e){
        presenter.gotoUser(profile.getId());
    }

    @UiHandler("addSkill")
    public void clickAddSkill(ClickEvent e){
        presenter.showSkillAddDialog();
    }

    @Override
    public void reloadSkills(Skill[] skillList , boolean disableSkill) {
        if(disableSkill){
            if(disableSkills == null){
                disableSkills = new FlexTable();
            }
            reloadSkillTable(skillList, disableSkills);
            disableSkillPanel.clear();
            disableSkillPanel.add(disableSkills);
            disableSkillsBox = null;
        } else {
            if(skills == null){
                skills = new FlexTable();
            }
            reloadSkillTable(skillList, skills);
            skillsPanel.setWidget(skills);
        }

    }

    private void reloadSkillTable(Skill[] skillList ,final FlexTable skillTable){
        if(skillTable.getRowCount() <= 1){
            initSkillTable(skillTable);
        }else {
            for(int i = 1; i < skillTable.getRowCount();i++){
                skillTable.removeRow(i);
            }
        }
        if (skillList.length <= 0) {
            skillTable.setText(1, 0, "スキルはまだありません.");
            skillTable.getFlexCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
            skillTable.getFlexCellFormatter().setColSpan(1, 0, skillTable.getCellCount(0));
            if (login.isLoggedIn()
                    && login.getProfile().getUserEmail().equals(profile.getUserEmail())) {
                skillTable.setWidget(2, 0, injector.getAppealAnchor());
                skillTable.getFlexCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
                skillTable.getFlexCellFormatter().setColSpan(2, 0, skillTable.getCellCount(0));
            }

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

            skillTable.setWidget(j, 0, name);
            skillTable.setText(j, 1, String.valueOf(skill.getAgreedCount()));
            skillTable.setText(j, 2, skill.getPoint().toString());
            skillTable.setText(j, 3, skill.getDescription());

            presenter.getSkillRelations(skill , new AsyncCallback<SkillRelation[]>() {
                @Override
                public void onSuccess(final SkillRelation[] rs) {

                    skillTable.setWidget(j, 4, makeAgreedButton(skill, rs));

                    final Anchor showComment = new Anchor("詳細");
                    skillTable.setWidget(j, 5, showComment);
                    showComment.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            makeAgrees(skill,skillTable, rs, j);
                            showComment.setVisible(false);
                        }
                    });
                }

                @Override
                public void onFailure(Throwable caught) {
                    skillTable.setText(j, 4, "データを取得できませんでした");
                }
            });
        }
    }

    private void initSkillTable(FlexTable skillTable) {
        skillTable.addStyleName("skill-grid");
        skillTable.setText(0, 0, "スキル");
        skillTable.setText(0, 1, "賛同者");
        skillTable.setText(0, 2, "ポイント");
        skillTable.setText(0, 3, "説明");
        skillTable.getCellFormatter().addStyleName(0, 0, "skill-name");
        skillTable.getCellFormatter().addStyleName(0, 1, "skill-agreed");
        skillTable.getCellFormatter().addStyleName(0, 2, "skill-point");
        skillTable.getCellFormatter().addStyleName(0, 3, "skill-description");
        skillTable.getCellFormatter().addStyleName(0, 4, "skill-agree-link");
        skillTable.getCellFormatter().addStyleName(0, 5, "skill-detail");
        skillTable.getRowFormatter().addStyleName(0, "grid-columns");
    }

    @UiHandler("disableSkillPanel")
    public void onDisableSkillPanelOpen(OpenEvent<DisclosurePanel> event){
        event.getTarget().setOpen(true);

        if(disableSkillsBox == null){
            return;
        }

        presenter.reloadDisableSkills();

    }

    private void makeAgrees(final Skill skill , FlexTable skillTable, SkillRelation[] rs, int j) {
        VerticalPanel panel = new VerticalPanel();
        for (int i = 0; i < rs.length; i ++) {
            SkillRelation sr = rs[i];
            Profile p = sr.getProfile();
            UserThumnail userThumnail = utProvider.get();
            userThumnail.setUser(p);
            panel.add(userThumnail);
            Label agreeComment = new Label(sr.getComment());
            agreeComment.addStyleName("agree-comment");
            panel.add(agreeComment);
        }
        skillTable.setWidget(j, 5, panel);
    }

    private Widget makeAgreedButton(final Skill skill, SkillRelation[] rs) {
        if (!login.isLoggedIn()
                || !login.getProfile().isActivate()
                || login.getEmailAddress().equals(profile.getUserEmail())) {
            return null;
        }

        for (final SkillRelation rel : rs) {
            if (rel.getUserEmail().equals(login.getEmailAddress())) {
                if (rel.getPoint() != null && rel.getPoint() >= 10L) {         // ここはアドホックにマジックリテラルしてるけど本当はもっとやり方を考えたい
                    return new Label("賛同済み");
                }
                Anchor anchor = new Anchor("ポイント加算");
                anchor.setTitle("このスキルにポイントを加算します");
                anchor.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        rel.setPoint(10L);
                        presenter.showAgreedDialog(skill, rel);
                    }
                });

                return anchor;
            }
        }

        Anchor anchor = new Anchor("賛同する");
        anchor.setTitle("このスキルに賛同します");
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.showAgreedDialog(skill, new SkillRelation());
            }
        });

        return anchor;
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
