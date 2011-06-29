package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.event.SkillAddSubmitEvent;
import com.appspot.skillmaps.shared.model.SkillA;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SkillAddDialog extends DialogBox implements Editor<SkillA> {

    interface SkillAddStyle extends CssResource{
        String form();
    }

    @UiField SkillAddStyle style;

    @UiField(provided=true)
    @Editor.Path("name")
    SuggestBox skillName;

    MultiWordSuggestOracle skillNames = new MultiWordSuggestOracle();

    @UiField
    Button submit;

    @UiField
    Button cancel;

    private static SkillAddDialogUiBinder uiBinder =
        GWT.create(SkillAddDialogUiBinder.class);

    private final EventBus eventBus;

    interface SkillAddDialogUiBinder extends UiBinder<Widget, SkillAddDialog> {
    }

    @Inject
    public SkillAddDialog(EventBus eventBus) {
        this.eventBus = eventBus;
        skillName = new SuggestBox(skillNames);
        setAutoHideEnabled(true);
        setAutoHideOnHistoryEventsEnabled(true);
        add(uiBinder.createAndBindUi(this));
//        this.addStyleName(style.form());
    }

    public void setSkillNames(SkillMap[] result){
        if(result == null || result.length == 0){
            return;
        }
        skillNames.clear();
        for (SkillMap skillMap : result) {
            skillNames.add(skillMap.getSkillName());
        }
    }

    @UiHandler("submit")
    public void clickSubmit(ClickEvent e){
        submit.setEnabled(false);
        eventBus.fireEvent(new SkillAddSubmitEvent());
    }

    @UiHandler("cancel")
    public void clickCancel(ClickEvent e){
        hide();
    }

}
