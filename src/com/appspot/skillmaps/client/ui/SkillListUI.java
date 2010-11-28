package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.SkillListDisplay;
import com.appspot.skillmaps.client.presenter.SkillOwnersActivity;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class SkillListUI extends Composite implements SkillListDisplay{

    private static SkillListUiBinder uiBinder = GWT
        .create(SkillListUiBinder.class);

    interface SkillListUiBinder extends UiBinder<Widget, SkillListUI> {
    }

    @UiField
    FlowPanel skillsPanel;

    private Presenter presenter;

    private final Provider<SkillOwnersActivity> skillOwnersProvider;

    private final EventBus eventBus;

    private final Provider<Anchor> permalinkProvider;

    @Inject
    public SkillListUI(Login login,
                       EventBus eventBus,
                       @Named("skillOwnersPermalink") Provider<Anchor>  permalinkProvider,
                       Provider<SkillOwnersActivity> skillOwnersProvider) {
        this.eventBus = eventBus;
        this.permalinkProvider = permalinkProvider;
        this.skillOwnersProvider = skillOwnersProvider;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setSkillMaps(SkillMap[] skillMaps){
        for (final SkillMap sm : skillMaps) {
            Anchor skill = new Anchor(sm.getSkillName() + "(" + sm.getPoint() + ")");

            skill.addStyleName(makeStyleForSkillName(sm.getPoint()));

            skill.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    SkillMapPopupPanel skillOwners = new SkillMapPopupPanel();
                    SkillOwnersActivity skillOwnersActivity = skillOwnersProvider.get();
                    skillOwnersActivity.setSkillName(sm.getSkillName());
                    skillOwnersActivity.initDisplay(skillOwners.getContents(), eventBus);
                    Anchor permalink = permalinkProvider.get();
                    permalink.setName(sm.getSkillName());
                    skillOwners.setFooter(permalink);
                    skillOwners.center();
                    skillOwners.show();
                }
            });
            skillsPanel.add(skill);
        }

    }

    private String makeStyleForSkillName(long point) {
        if (point > 40) return "mega";
        if (point > 20) return "big";
        if (point > 10) return "middle";
        return "small";
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }
}
