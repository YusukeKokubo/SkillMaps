package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.UserUIDisplay;
import com.appspot.skillmaps.client.display.UserUIDisplay.Presenter;
import com.appspot.skillmaps.client.event.SkillAddSubmitEvent;
import com.appspot.skillmaps.client.event.SkillAddSubmitHandler;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.SkillAddDialog;
import com.appspot.skillmaps.shared.model.Profile;
import com.appspot.skillmaps.shared.model.Skill;
import com.appspot.skillmaps.shared.model.SkillRelation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserUIActivity extends SkillMapActivity implements Presenter {

    public interface SkillDriver extends SimpleBeanEditorDriver<Skill, SkillAddDialog>{}

    SkillDriver skillDriver = GWT.create(SkillDriver.class);

    private final Provider<UserUIDisplay> displayProvider;
    private final Provider<SkillAddDialog> skillAddDialogProvider;

    private Profile profile;

    private EventBus eventBus;

    private final Provider<SkillServiceAsync> serviceProvider;

    private UserUIDisplay display;


    @Inject
    public UserUIActivity(Provider<UserUIDisplay> displayProvider,
                          Provider<SkillAddDialog> skillAddDialogProvider,
                          Provider<SkillServiceAsync> serviceProvider){
        this.displayProvider = displayProvider;
        this.skillAddDialogProvider = skillAddDialogProvider;
        this.serviceProvider = serviceProvider;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }

    @Override
    public void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        this.eventBus = eventBus;
        display = displayProvider.get();
        display.setProfile(profile);
        panel.setWidget(display);
    }

    @Override
    public void start(final AcceptsOneWidget panel,final EventBus eventBus) {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {
                initDisplay(panel, eventBus);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    @Override
    public void showSkillAddDialog() {
        final SkillAddDialog skillAddDialog = skillAddDialogProvider.get();
        skillDriver.initialize(skillAddDialog);
        skillDriver.edit(new Skill());
        final HandlerRegistration hr = eventBus.addHandler(SkillAddSubmitEvent.TYPE, new SkillAddSubmitHandler() {

            @Override
            public void onSubmit(SkillAddSubmitEvent e) {
                Skill skill = skillDriver.flush();

                serviceProvider.get().putSkill(skill, new SkillRelation(), true, new AsyncCallback<Void>() {

                    @Override
                    public void onSuccess(Void arg0) {
                        Window.alert("追加しました");
                        display.reloadSkills(null);//TODO
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                });


            }
        });
        skillAddDialog.center();
    }

    @Override
    public void reloadSkills() {
        // TODO 自動生成されたメソッド・スタブ

    }

}
