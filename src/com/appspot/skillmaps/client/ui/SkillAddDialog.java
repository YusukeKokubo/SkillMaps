package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.event.SkillAddSubmitEvent;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillAddDialog extends DialogBox implements Editor<Skill> {
    @UiField(provided=true)
    SuggestBox skillName;

    MultiWordSuggestOracle skillNames = new MultiWordSuggestOracle();

    @UiField
    TextArea description;

    @UiField
    TextArea comment;

    @UiField
    Button submit;

    @UiField
    Button cancel;

    private static SkillAddDialogUiBinder uiBinder =
        GWT.create(SkillAddDialogUiBinder.class);

    private final Provider<SkillServiceAsync> serviceProvider;

    private Profile profile;

    private final EventBus eventBus;

    interface SkillAddDialogUiBinder extends UiBinder<Widget, SkillAddDialog> {
    }

    @Inject
    public SkillAddDialog(Provider<SkillServiceAsync> serviceProvider,
                          EventBus eventBus) {
        this.serviceProvider = serviceProvider;
        this.eventBus = eventBus;
        add(uiBinder.createAndBindUi(this));
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }

    @Override
    public void center(){

        serviceProvider.get().getSkillNames(new AsyncCallback<SkillMap[]>() {

            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(SkillMap[] result) {
                if(result == null || result.length == 0){
                    return;
                }
                skillNames.clear();
                for (SkillMap skillMap : result) {
                    skillNames.add(skillMap.getSkillName());
                }
            }
        });
        skillName.setText("");
        description.setText("");
        comment.setText("");
        center();
    }

    @UiHandler("submit")
    public void clickSubmit(ClickEvent e){
        submit.setEnabled(false);
        eventBus.fireEvent(new SkillAddSubmitEvent());
//        Skill skill = new Skill();
//        skill.setOwnerEmail(profile.getUserEmail());
//        skill.setName(skillName.getText());
//        skill.setDescription(description.getText());
//        SkillRelation rel = new SkillRelation();
//        rel.setComment(comment.getText());
//        serviceProvider.get().putSkill(skill, rel, true, new AsyncCallback<Void>() {
//            @Override
//            public void onSuccess(Void result) {
//                Window.alert("追加しました");
//                submit.setEnabled(true);
////                reloadSkills();
//            }
//
//            @Override
//            public void onFailure(Throwable caught) {
//                Window.alert(caught.getMessage() + "\n" + caught.getStackTrace());
//                submit.setEnabled(true);
//            }
//        });
////        reloadSkills();
    }

    public void clickCancel(ClickEvent e){
        hide();
    }


}
