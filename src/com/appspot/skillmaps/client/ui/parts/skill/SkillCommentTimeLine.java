package com.appspot.skillmaps.client.ui.parts.skill;

import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.ui.SkillMapPopupPanel;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillCommentTimeLine extends Composite {

    private static SkillCommentTimeLineUiBinder uiBinder =
        GWT.create(SkillCommentTimeLineUiBinder.class);

    interface SkillCommentTimeLineUiBinder extends
            UiBinder<Widget, SkillCommentTimeLine> {
    }

    @UiField
    Label skillName;

    @UiField
    Anchor userName;

    @UiField(provided=true)
    SkillCommentThumnail skillCommentThumnail;

    @Inject
    Provider<SkillCommentThumnail> timeLineProvider;

    @Inject
    Provider<UserUIDisplay> userUiProvider;

    @Inject
    public SkillCommentTimeLine() {
    }

    public void setSkillComment(SkillComment skillComment){
        skillCommentThumnail = timeLineProvider.get();
        skillCommentThumnail.setSkillComment(skillComment);
        initWidget(uiBinder.createAndBindUi(this));
        skillName.setText("Skill:" +  skillComment.getSkill().getModel().getName());
        final Profile user = skillComment.getSkill().getModel().getProfile();
        userName.setText(user.getId());

        userName.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SkillMapPopupPanel userDialog = new SkillMapPopupPanel();
                UserUIDisplay uiDisplay = userUiProvider.get();
                userDialog.setContents(uiDisplay);
                uiDisplay.setProfile(user);
                userDialog.center();
            }
        });
    }

}
