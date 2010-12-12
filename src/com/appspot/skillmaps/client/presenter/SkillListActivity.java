package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.display.SkillListDisplay;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillListActivity extends SkillMapActivity implements SkillListDisplay.Presenter {

    private final Provider<SkillListDisplay> displayProvider;
    private final Provider<SkillServiceAsync> serviceProvider;
    private SkillListDisplay display;

    @Inject
    public SkillListActivity(Provider<SkillListDisplay> displayProvider,
                             Provider<SkillServiceAsync> serviceProvider
                             ) {
        this.displayProvider = displayProvider;
        this.serviceProvider = serviceProvider;
    }

    private void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        display = displayProvider.get();
        display.setPresenter(this);
        panel.setWidget(display);
        serviceProvider.get().getSkillNames(new AsyncCallback<SkillMap[]>() {

            @Override
            public void onSuccess(SkillMap[] result) {
                display.setSkillMaps(result);
            }

            @Override
            public void onFailure(Throwable caught) {

            }
        });
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

    @Override
    public void setDisplay(SkillListDisplay display) {
        // TODO 自動生成されたメソッド・スタブ

    }

}
