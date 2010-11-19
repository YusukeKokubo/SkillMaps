package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.RecentAddedSkillsDisplay;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class RecentAddedSkillsActivity extends AbstractActivity implements RecentAddedSkillsDisplay.Presenter {


    private final Provider<RecentAddedSkillsDisplay> displayProvider;

    @Inject
    public RecentAddedSkillsActivity(Provider<RecentAddedSkillsDisplay> displayProvider) {
        this.displayProvider = displayProvider;
    }

    @Override
    public void start(final AcceptsOneWidget panel,final EventBus eventBus) {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {
                initDisplay(panel, eventBus);
            }

            @Override
            public void onFailure(Throwable reason) {
                UiMessage.info("画面表示に失敗しました。");
            }
        });

    }

    @Override
    public void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(displayProvider.get());
    }

}
