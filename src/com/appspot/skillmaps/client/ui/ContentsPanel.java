package com.appspot.skillmaps.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ContentsPanel extends Composite {

    private static ContentsPanelUiBinder uiBinder =
        GWT.create(ContentsPanelUiBinder.class);

    @UiField(provided=true)
    @Inject
    @Named("contents")
    SimplePanel mainPanel;

    @UiField(provided=true)
    @Inject
    @Named("dashboard")
    SimplePanel dashboard;

    interface ContentsPanelUiBinder extends UiBinder<Widget, ContentsPanel> {
    }

    @Inject
    public ContentsPanel() {
    }

    public void init(){
        initWidget(uiBinder.createAndBindUi(this));
        dashboard.getElement().setId("dashboard");
    }
}
