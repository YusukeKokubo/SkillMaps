package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.event.AgreedSubmitEvent;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AgreedForm extends DialogBox implements Editor<SkillRelation>{

    private static AgreedFormUiBinder uiBinder =
        GWT.create(AgreedFormUiBinder.class);

    @UiField
    Button submit;

    @UiField
    @Editor.Ignore
    TextArea comment;

    private final EventBus eventBus;

    interface AgreedFormUiBinder extends UiBinder<Widget, AgreedForm> {
    }

    @Inject
    public AgreedForm(EventBus eventBus) {
        this.eventBus = eventBus;
        add(uiBinder.createAndBindUi(this));
    }

    @UiHandler("submit")
    public void onSubmitButtonClick(ClickEvent e){
        submit.setEnabled(false);
        eventBus.fireEvent(new AgreedSubmitEvent());
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

    public HasText getComment(){
        return comment;
    }

}
