package com.appspot.skillmaps.client.ui.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class MessageController extends Composite implements HasWidgets {

    @UiField
    HasWidgets panel;

    List<Widget> widgets = new ArrayList<Widget>();

    private static MessageControllerUiBinder uiBinder =GWT.create(MessageControllerUiBinder.class);

    interface MessageControllerUiBinder extends UiBinder<Widget, MessageController> {}

    public MessageController() {
        initWidget(uiBinder.createAndBindUi(this));
        setVisible(false);
        getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
    }

    @Override
    public void add(Widget w) {
        MessageWidget messageWidget = new MessageWidget(w);
        widgets.add(messageWidget);
        Open openAnimetion = new Open(messageWidget);
        panel.add(messageWidget);
        setVisible(true);
        openAnimetion.run(500);
    }

    @Override
    public void clear() {
        widgets.clear();
        panel.clear();
        setVisible(false);
    }

    @Override
    public Iterator<Widget> iterator() {
        return widgets.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        boolean ret = panel.remove(w) && widgets.remove(w);

        if(widgets.isEmpty()){
            setVisible(false);
        }
        return ret;
    }

    private class Open extends Animation{

        private final MessageWidget mw;

        private static final int WIDGET_HEIGHT = 50;

        private Timer closeTimer = new Timer() {

            @Override
            public void run() {
                new Close(mw).run(3000);
            }
        };

        public Open(MessageWidget mw){
            mw.setVisible(false);
            this.mw = mw;
            mw.setHeight("0px");
            mw.getElement().getStyle().setTop((widgets.size() - 1) * WIDGET_HEIGHT , Unit.PX);

            mw.addDomHandler(
                new MouseOverHandler() {

                @Override
                public void onMouseOver(MouseOverEvent event) {
                    closeTimer.cancel();
                }
            },MouseOverEvent.getType());

            mw.addDomHandler(
                new MouseOutHandler() {

                @Override
                public void onMouseOut(MouseOutEvent event) {
                    onComplete();
                }
            },MouseOutEvent.getType());

        }
        @Override
        protected void onUpdate(double progress) {
            mw.setHeight((WIDGET_HEIGHT * progress) + "px");
        }

        @Override
        protected void onStart() {
            super.onStart();
            mw.setVisible(true);
        }

        @Override
        protected void onComplete() {
            super.onComplete();
            closeTimer.schedule(3000);
        }

    }

    private class Close extends Animation{

        private final MessageWidget mw;

        public Close(MessageWidget mw) {
            this.mw = mw;
        }

        @Override
        protected void onUpdate(double progress) {
            mw.getElement().getStyle().setOpacity(0.9 * (1- progress));
        }

        @Override
        protected void onComplete() {
            super.onComplete();
            remove(mw);
        }
    }
}
