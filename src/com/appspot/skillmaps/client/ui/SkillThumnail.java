package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SkillThumnail extends Composite {

    private static SkillThumnailUiBinder uiBinder = GWT
        .create(SkillThumnailUiBinder.class);

    interface SkillThumnailUiBinder extends UiBinder<Widget, SkillThumnail> {
    }

    @UiField
    PopupPanel skillOwners;

    @UiField
    Anchor skillName;
    
    @UiField
    UserThumnail profile;

    Skill skill;
    
    Login login;
    
    public SkillThumnail(final Login login, final Skill skill) {
        this.login = login;
        this.skill = skill;
        initWidget(uiBinder.createAndBindUi(this));
        final Anchor close = new Anchor("close");
        close.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                skillOwners.hide();
            }
        });

        skillName.setText(skill.getName());
        skillName.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                VerticalPanel panel = new VerticalPanel();
                panel.add(close);
                panel.add(new SkillOwners(login, skill.getName()));
                panel.add(new Anchor("permalink", "/skill.html?name="
                    + URL.encodeComponent(skill.getName())));
                skillOwners.setWidget(panel);
                skillOwners.center();
            }
        });
    }
    
    @UiFactory
    UserThumnail makeProfile() {
        return new UserThumnail(login, skill.getProfile(), skillOwners);
    }
}
