package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.display.SkillListDisplay;
import com.appspot.skillmaps.client.presenter.SkillOwnersActivity;
import com.appspot.skillmaps.shared.model.Login;
import com.appspot.skillmaps.shared.model.SkillMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class SkillListUI extends Composite implements SkillListDisplay{

    private static SkillListUiBinder uiBinder = GWT
        .create(SkillListUiBinder.class);

    interface SkillListUiBinder extends UiBinder<Widget, SkillListUI> {
    }

    public interface Style extends CssResource{
        String tagCloud();
        String level1();
        String level2();
        String level3();
        String level4();
        String level5();
    }

    @UiField
    Style style;

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
            Anchor skill = new Anchor(sm.getSkillName() + "(" + sm.getPoint() + ") ");
            FocusPanel li = new FocusPanel();
            li.addStyleName(makeStyleForSkillName(sm.getPoint()));
            ClickHandler clickHandler = new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    SkillMapPopupPanel skillOwners = new SkillMapPopupPanel();
                    SkillOwnersActivity skillOwnersActivity = skillOwnersProvider.get();
                    skillOwnersActivity.setSkillName(sm.getSkillName());
                    skillOwnersActivity.start(skillOwners.getContents(), eventBus);
                    Anchor permalink = permalinkProvider.get();
                    permalink.setName(sm.getSkillName());
                    skillOwners.setFooter(permalink);
                    skillOwners.center();
                    skillOwners.show();
                }
            };

            li.addClickHandler(clickHandler);
            skill.addClickHandler(clickHandler);
            li.setWidget(skill);
            skillsPanel.add(li);
        }

    }

    private String makeStyleForSkillName(long point) {
        if (point > 400) return style.level1();
        if (point > 250) return style.level2();
        if (point > 150) return style.level3();
        if (point > 30) return style.level4();
        return style.level5();
    }

    @Override
    @Inject
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDisplay(this);
    }
}
