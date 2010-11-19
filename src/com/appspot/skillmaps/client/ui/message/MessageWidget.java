package com.appspot.skillmaps.client.ui.message;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class MessageWidget extends Composite {

    private static MessageWidgetUiBinder uiBinder =
        GWT.create(MessageWidgetUiBinder.class);

    interface MessageWidgetUiBinder extends UiBinder<Widget, MessageWidget> {
    }

    @UiField
    HasWidgets panel;

    public MessageWidget(Widget w) {
        initWidget(uiBinder.createAndBindUi(this));
        panel.add(w);
    }
}
