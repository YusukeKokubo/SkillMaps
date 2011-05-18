package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.bundle.Resources;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.parts.top.TimeLinePanel;
import com.appspot.skillmaps.shared.model.SkillComment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TimeLineActivity extends SkillMapActivity {

    @Inject
    Injector injector;

    @Inject
    SkillServiceAsync skillService;

    @Inject
    Resources res;

    @Inject
    Provider<TimeLinePanel> timeLineProvider;

    @Inject
    public TimeLineActivity(){
    }

    @Override
    public void start(final AcceptsOneWidget panel,final EventBus eventBus) {
        panel.setWidget(new Image(res.loader()));
        GWT.runAsync(new RunAsyncCallback() {
            @Override
            public void onSuccess() {
                initPresenter(panel, eventBus);
            }

            @Override
            public void onFailure(Throwable reason) {
            }
        });
    }

    public void initPresenter(final AcceptsOneWidget panel,final EventBus eventBus) {
        skillService.getRecentAddedSkillComment(new AsyncCallback<SkillComment[]>() {
            @Override
            public void onSuccess(SkillComment[] result) {
                TimeLinePanel timeLinePanel = timeLineProvider.get();
                timeLinePanel.setSkillComments(result);
                panel.setWidget(timeLinePanel);
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }
}
