package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.SkillAppealTimeLineDisplay;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillAppealTimeLineActivity extends AbstractActivity implements
        SkillAppealTimeLineDisplay.Presenter {

    private final Provider<SkillAppealTimeLineDisplay> displayProvider;
    private final Provider<SkillServiceAsync> serviceProvider;

    @Inject
    public SkillAppealTimeLineActivity(
            Provider<SkillAppealTimeLineDisplay> displayProvider,
            Provider<SkillServiceAsync> serviceProvider) {
        this.displayProvider = displayProvider;
        this.serviceProvider = serviceProvider;

    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        initDisplay(panel, eventBus);
    }

    @Override
    public void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        final SkillAppealTimeLineDisplay display = displayProvider.get();
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

}
