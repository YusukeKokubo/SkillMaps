package com.appspot.skillmaps.client.ui.form.skill;

import com.appspot.skillmaps.client.event.SkillCommentAddSubmitEvent;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SkillCommentForm extends DialogBox implements Editor<SkillComment> {

    private static SkillCommentFormUiBinder uiBinder = GWT.create(SkillCommentFormUiBinder.class);

    interface SkillCommentFormUiBinder extends UiBinder<Widget, SkillCommentForm> {}

    interface Driver extends SimpleBeanEditorDriver<SkillComment, SkillCommentForm>{};

    Driver driver = GWT.create(Driver.class);

    @UiField
    Button submit;

    @UiField
    TextArea comment;

    Key skillKey;

    @Inject
    Injector injector;

    @Inject
    EventBus eventBus;

    @Inject
    public SkillCommentForm() {
        add(uiBinder.createAndBindUi(this));
        this.setAnimationEnabled(true);
        this.setText("コメントを追加します。");
        driver.initialize(this);
    }

    public void setSkillComment(Key skillKey, SkillComment skillComment){
        this.skillKey = skillKey;
        if(skillComment == null){
            skillComment = new SkillComment();
            skillComment.getSkill().setKey(skillKey);
        }
        driver.edit(skillComment);
    }

    @UiHandler("submit")
    public void onSubmitButtonClick(ClickEvent e){
        submit.setEnabled(false);
        eventBus.fireEvent(new SkillCommentAddSubmitEvent());
    }

    @Override
    public void center() {
        submit.setEnabled(true);
        super.center();
    }

    @UiHandler("cancel")
    public void onCancelButtonClick(ClickEvent e){
        hide();
    }

    public SkillComment getComment(){
        return driver.flush();
    }
}
