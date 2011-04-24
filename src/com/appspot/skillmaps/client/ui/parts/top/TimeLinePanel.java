package com.appspot.skillmaps.client.ui.parts.top;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.ui.parts.skill.SkillCommentTimeLine;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TimeLinePanel extends Composite {

    private static TimeLinePanelUiBinder uiBinder =
        GWT.create(TimeLinePanelUiBinder.class);

    @UiField
    VerticalPanel timeLinePanel;

    Resources res = Resources.INSTANCE;

    @Inject
    Provider<SkillCommentTimeLine> timeLineProvier;

    interface TimeLinePanelUiBinder extends UiBinder<Widget, TimeLinePanel> {
    }

    @Inject
    public TimeLinePanel() {
        initWidget(uiBinder.createAndBindUi(this));
        timeLinePanel.clear();
        Image image = new Image(res.loader());
        timeLinePanel.add(image);
        timeLinePanel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
    }

    public void setSkillComments(SkillComment[] skillCommens){
        timeLinePanel.clear();
        for (SkillComment skillComment : skillCommens) {

            SkillCommentTimeLine timeLine = timeLineProvier.get();

            timeLine.setSkillComment(skillComment);

            timeLinePanel.add(timeLine);
        }
    }

}
