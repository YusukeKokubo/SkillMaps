package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.SkillAppealTimeLineDisplay;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillAppealTimeLineActivity extends SkillMapActivity implements
        SkillAppealTimeLineDisplay.Presenter {

    private final Provider<SkillAppealTimeLineDisplay> displayProvider;
    private final Provider<SkillServiceAsync> serviceProvider;
    private SkillAppealTimeLineDisplay display;

    @Inject
    public SkillAppealTimeLineActivity(
            Provider<SkillAppealTimeLineDisplay> displayProvider,
            Provider<SkillServiceAsync> serviceProvider) {
        this.displayProvider = displayProvider;
        this.serviceProvider = serviceProvider;

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
                // TODO 自動生成されたメソッド・スタブ
            }
        });
    }

    private void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        setDisplay(displayProvider.get());
        display.setPresenter(this);
        panel.setWidget(display);
        serviceProvider.get().getSkillAppeals(
            new AsyncCallback<SkillAppeal[]>() {
                @Override
                public void onSuccess(SkillAppeal[] as) {
                    display.setSkillAppeals(as);
                }

                @Override
                public void onFailure(Throwable caught) {
                    UiMessage.severe("スキルアピールの取得に失敗しました。", caught);
                    // Window.alert(caught.getMessage() + "\n" +
                    // caught.getStackTrace());
                }
            });
    }

    @Override
    public void setDisplay(SkillAppealTimeLineDisplay display) {
        this.display = display;
    }
}
