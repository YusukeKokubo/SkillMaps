package com.appspot.skillmaps.client.ui.parts.skill;

import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.client.ui.UserThumnail;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SkillCommentThumnail extends Composite implements Editor<SkillComment> {

    private static SkillCommentThumnailUiBinder uiBinder = GWT.create(SkillCommentThumnailUiBinder.class);

    interface SkillCommentThumnailUiBinder extends UiBinder<Widget, SkillCommentThumnail> {}

    interface Driver extends SimpleBeanEditorDriver<SkillComment, SkillCommentThumnail> {}

    Driver driver = GWT.create(Driver.class);

    @UiField
    Label comment;

    @UiField(provided=true)
    @Editor.Ignore
    UserThumnail profile;

    private final Injector injector;

    @Inject
    public SkillCommentThumnail(Injector injector) {
        this.injector = injector;
        profile = this.injector.getUserThumnail();
        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    public void setSkillComment(SkillComment comment){
        profile.setUser(comment.getProfile());
        driver.edit(comment);
    }

    public SkillComment getSkillComment(){
        return driver.flush();
    }
}
