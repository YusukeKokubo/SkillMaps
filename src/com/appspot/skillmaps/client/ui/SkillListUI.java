package com.appspot.skillmaps.client.ui;

import com.appspot.skillmaps.client.service.SkillService;
import com.appspot.skillmaps.client.service.SkillServiceAsync;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SkillListUI extends Composite {

    private static SkillListUiBinder uiBinder = GWT
        .create(SkillListUiBinder.class);

    private final SkillServiceAsync service = GWT.create(SkillService.class);

    interface SkillListUiBinder extends UiBinder<Widget, SkillListUI> {
    }
    
    @UiField
    FlowPanel skillsPanel;
    
    @UiField
    PopupPanel skillOwners;

    public SkillListUI(final Login login) {
        initWidget(uiBinder.createAndBindUi(this));
        final Anchor close = new Anchor("close");
        close.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                skillOwners.hide();
            }
        });
        
        service.getSkillNames(new AsyncCallback<String[]>() {
            @Override
            public void onSuccess(String[] skillNames) {
                for (final String name : skillNames) {
                    Anchor skill = new Anchor(name);
                    skill.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            VerticalPanel panel = new VerticalPanel();
                            panel.add(close);
                            panel.add(new SkillOwners(login, name));
                            panel.add(new Anchor("permalink", "/skill.html?name=" + URL.encodeComponent(name)));
                            skillOwners.setWidget(panel);
                            skillOwners.center();
                        }
                    });
                    skillsPanel.add(skill);
                }
            }
            
            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

}
