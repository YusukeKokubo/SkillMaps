package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.SkillOwnersDisplay;
import com.appspot.skillmaps.client.place.SkillOwnersPlace;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Skill;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillOwnersActivity extends SkillMapActivity implements SkillOwnersDisplay.Presenter {

    private String skillName;
    private final Provider<SkillOwnersDisplay> displayProvider;
    private SkillOwnersDisplay display;
    private final Provider<SkillServiceAsync> serviceProvider;

    @Inject
    public SkillOwnersActivity(Provider<SkillOwnersDisplay> displayProvider,
                               Provider<SkillServiceAsync> serviceProvider) {
        this.displayProvider = displayProvider;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(final AcceptsOneWidget panel,final EventBus eventBus) {
        panel.setWidget(new Image(Resources.INSTANCE.loader()));
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {
                initDisplay(panel, eventBus);
            }

            @Override
            public void onFailure(Throwable reason) {
                // TODO 自動生成されたメソッド・スタブ

            }
        });
    }

    private void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        setDisplay(displayProvider.get());

        display.setPresenter(this);


        serviceProvider.get().getSkillOwners(skillName, new AsyncCallback<Skill[]>() {

            @Override
            public void onSuccess(Skill[] result) {
                display.setSkills(result);

            }

            @Override
            public void onFailure(Throwable caught) {

            }
        });
        panel.setWidget(display);

    }

    @Override
    public void setPlace(Place place) {
        if(place instanceof SkillOwnersPlace){
            SkillOwnersPlace sop = (SkillOwnersPlace)place;
            setSkillName(sop.getSkillName());
            super.setPlace(sop);
        }
    }

    @Override
    public void setSkillName(String skillName){
        this.skillName = skillName;
    }

    @Override
    public void setDisplay(SkillOwnersDisplay display) {
        this.display = display;

    }

}
