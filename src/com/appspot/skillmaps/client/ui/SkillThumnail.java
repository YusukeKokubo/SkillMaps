package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.presenter.SkillOwnersActivity;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class SkillThumnail extends Composite {

    private static SkillThumnailUiBinder uiBinder = GWT
        .create(SkillThumnailUiBinder.class);

    interface SkillThumnailUiBinder extends UiBinder<Widget, SkillThumnail> {
    }

    @UiField
    Anchor skillName;

    @UiField
    UserThumnail profile;

    Login login;

    SkillMapPopupPanel skillOwners;

    private final Provider<SkillOwnersActivity> skillOwnersProvider;

    private final EventBus eventBus;

    private final Provider<Anchor> permalinkProvider;

    private final Provider<UserThumnail> utProvider;

    @Inject
    public SkillThumnail(Login login,
                         Provider<SkillOwnersActivity> skillOwnersProvider,
                         Provider<UserThumnail> utProvider,
                         @Named("skillOwnersPermalink") Provider<Anchor>  permalinkProvider,
                         EventBus eventBus) {
        this.login = login;
        this.skillOwnersProvider = skillOwnersProvider;
        this.utProvider = utProvider;
        this.permalinkProvider = permalinkProvider;
        this.eventBus = eventBus;
        this.skillOwners = new SkillMapPopupPanel();
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setSkill(final Skill skill){
        profile.setUser(skill.getProfile());
        skillName.setText(skill.getName());
        skillName.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SkillOwnersActivity skillOwnersActivity = skillOwnersProvider.get();
                skillOwnersActivity.setSkillName(skill.getName());
                skillOwnersActivity.start(skillOwners.getContents(), eventBus);
                Anchor permalink = permalinkProvider.get();
                permalink.setName(skill.getName());
                skillOwners.setFooter(permalink);
                skillOwners.center();
            }
        });
    }

    @UiFactory
    UserThumnail makeProfile() {
        return utProvider.get();
    }
}
