package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.display.SkillAppealFormDisplay;
import com.appspot.skillmaps.client.inject.Injector;
import com.appspot.skillmaps.client.place.HomePlace;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.client.ui.SkillAppealForm;
import com.appspot.skillmaps.client.ui.message.UiMessage;
import com.appspot.skillmaps.shared.model.SkillAppeal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SkillAppealFormActivity extends SkillMapActivity implements
        SkillAppealFormDisplay.Presenter {

    private final Provider<SkillAppealFormDisplay> displayProvider;
    private final Provider<SkillServiceAsync> serviceProvier;
    private final Driver driver;
    private SkillAppealFormDisplay display;
    private final Injector injector;
    private final HomePlace homePlace;

    interface Driver extends
            SimpleBeanEditorDriver<SkillAppeal, SkillAppealForm> {
    }

    @Inject
    public SkillAppealFormActivity(
            Provider<SkillAppealFormDisplay> displayProvider,
            Provider<SkillServiceAsync> serviceProvier,
            Injector injector,
            HomePlace homePlace) {
        this.displayProvider = displayProvider;
        this.serviceProvier = serviceProvier;
        this.injector = injector;
        this.homePlace = homePlace;
        this.driver = GWT.create(Driver.class);
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        initDisplay(panel, eventBus);
    }

    @Override
    public void registSkillAppeal() {
        SkillAppeal skillAppeal = driver.flush();
        if (skillAppeal.getAppealSkillName().isEmpty()) {
            UiMessage.info("アピールするスキルを入力してください");
            return;
        }
        if (skillAppeal.getDescription().isEmpty()) {
            UiMessage.info("アピール文を入力してください");
            return;
        }

        serviceProvier.get().putSkillAppeal(
            skillAppeal,
            display.getSendTwitter().getValue(),
            new AsyncCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    UiMessage.info("アピールしました!");
                    injector.getPlaceController().goTo(homePlace);
                }

                @Override
                public void onFailure(Throwable caught) {
                    UiMessage.severe("アピールの登録に失敗しました。", caught);
                }
            });

    }

    @Override
    public void initDisplay(AcceptsOneWidget panel, EventBus eventBus) {
        setupDisplay(displayProvider.get());
        panel.setWidget(display);
    }

    @Override
    public void setupDisplay(SkillAppealFormDisplay display) {
        this.display = display;
        driver.initialize((SkillAppealForm)display);
        driver.edit(new SkillAppeal());
        display.setPresenter(this);

    }

}
