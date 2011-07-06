package com.appspot.skillmaps.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SkillMapPopupPanel extends Composite {

    @UiField
    protected VerticalPanel panel;

    @UiField
    protected SimplePanel headerPanel;

    @UiField
    protected SimplePanel footerPanel;

    @UiField
    protected SimplePanel contentsPanel;

    @UiField
    protected ScrollPanel scrollPanel;

    @UiField
    protected Anchor close;

    private static SkillMapPopupPanelUiBinder uiBinder =
        GWT.create(SkillMapPopupPanelUiBinder.class);

    interface SkillMapPopupPanelUiBinder extends
            UiBinder<Widget, SkillMapPopupPanel> {
    }

    public SkillMapPopupPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public SimplePanel getHeader() {
        return this.headerPanel;
    }

    public void setHeader(IsWidget w) {
        setHeader(w.asWidget());
    }

    public void setHeader(Widget w) {
        headerPanel.setWidget(w);
    }

    public SimplePanel getContents() {
        return this.contentsPanel;
    }

    public void setContents(IsWidget w) {
        setContents(w.asWidget());
    }

    public void setContents(Widget w) {
        contentsPanel.setWidget(w);
    }

    public SimplePanel getFooter() {
        return this.footerPanel;
    }

    public void setFooter(IsWidget w) {
        this.setFooter(w.asWidget());
    }

    public void setFooter(Widget w) {
        footerPanel.setWidget(w);
    }

    public void center() {
        try {
            RootPanel.get("dashboard").clear();
        } catch (Throwable e) {

        }
        RootPanel.get("dashboard").add(this);
    }

    public void show() {
        center();
    }

    @UiHandler("close")
    public void onCloseAnchorClick(ClickEvent e) {
        RootPanel.get("dashboard").clear();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Element parentElement =
            Document.get().getElementById("dashboard").getParentElement();

        scrollPanel.setWidth(parentElement.getOffsetWidth() + "px");
        scrollPanel.setHeight(parentElement.getOffsetHeight() + "px");

    }

}
