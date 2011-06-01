package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.client.ui.parts.user.UserSkillDetailPanel;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTMLPanel;
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
    Anchor githubLink;

    @UiField
    Label selfIntroduction;

    @UiField
    Anchor profileUrl1;

    @UiField
    Anchor profileUrl2;

    @UiField
    Image icon;

    @UiField
    Button addSkill;

    @UiField
    DisclosurePanel disableSkillPanel;

    @UiField
    HTMLPanel disableSkillsBox;

    Login login;

    Profile profile;

    private Presenter presenter;

    @UiField
    SimplePanel skillsPanel;

//    private final Provider<UserThumnail> utProvider;

    private final Injector injector;

    @Inject
    public UserUI(Login login ,
                  Provider<UserThumnail> utProvider,
                  Injector injector) {
        this.login = login;
//        this.utProvider = utProvider;
        this.injector = injector;
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
            twitterLink.setHref("https://twitter.com/" + profile.getTwitterScreenName());
        }
        if (profile.isEnabledGitHub()) {
            githubLink.setText("github : " + profile.getGithubLogin());
            githubLink.setHref("https://github.com/" + profile.getGithubLogin());
        }

        if (!login.isLoggedIn() || login.getProfile().equals(profile)) {
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
        VerticalPanel panel = new VerticalPanel();
        if(disableSkill){
            disableSkillPanel.clear();
            disableSkillPanel.add(panel);
        } else {
            skillsPanel.setWidget(panel);
        }
        panel.setWidth("100%");

        for (Skill skill : skillList) {
            UserSkillDetailPanel userSkillDetailPanel = injector.getUserSkillDetailPanel();
            userSkillDetailPanel.setPresenter(presenter);
            skill.setProfile(profile);
            userSkillDetailPanel.setSkill(skill);
            panel.add(userSkillDetailPanel);
        }
    }

    @UiHandler("disableSkillPanel")
    public void onDisableSkillPanelOpen(OpenEvent<DisclosurePanel> event){
        event.getTarget().setOpen(true);
        if(disableSkillsBox == null){
            return;
        }
        presenter.reloadDisableSkills();

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
