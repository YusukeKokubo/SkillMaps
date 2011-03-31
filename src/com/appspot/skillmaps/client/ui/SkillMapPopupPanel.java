package com.appspot.skillmaps.client.ui;

import com.google.gwt.benchmarks.client.Setup;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
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
    protected Anchor close;


    private static SkillMapPopupPanelUiBinder uiBinder =
        GWT.create(SkillMapPopupPanelUiBinder.class);

    private Timer timer;

    interface SkillMapPopupPanelUiBinder extends
            UiBinder<Widget, SkillMapPopupPanel> {
    }

    public SkillMapPopupPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public SimplePanel getHeader(){
        return this.headerPanel;
    }

    public void setHeader(IsWidget w){
        setHeader(w.asWidget());
    }

    public void setHeader(Widget w){
        headerPanel.setWidget(w);
    }

    public SimplePanel getContents(){
        return this.contentsPanel;
    }

    public void setContents(IsWidget w){
        setContents(w.asWidget());
    }

    public void setContents(Widget w){
        contentsPanel.setWidget(w);
    }

    public SimplePanel getFooter(){
        return this.footerPanel;
    }

    public void setFooter(IsWidget w){
        this.setFooter(w.asWidget());
    }

    public void setFooter(Widget w){
        footerPanel.setWidget(w);
    }

    public void center(){
        try{
            RootPanel.get("dashboard").clear();
        }catch(Exception e){

        }
        RootPanel.get("dashboard").add(this);
        setScrollbar();
        timer = new Timer() {

            @Override
            public void run() {
                setScrollbar();
            }
        };

        //TODO あまりいい方法じゃない
        timer.scheduleRepeating(1000);
    }

    public void show(){
        center();
    }

    @UiHandler("close")
    public void onCloseAnchorClick(ClickEvent e){
        RootPanel.get("dashboard").clear();
    }

    public native void setScrollbar()/*-{
        $wnd.$("#mcs_container").mCustomScrollbar("vertical",0,"easeOutCirc",1.05,"fixed","yes","no",0);
    }-*/;

    @Override
    protected void onDetach() {
        timer.cancel();
        super.onDetach();
    }

}
