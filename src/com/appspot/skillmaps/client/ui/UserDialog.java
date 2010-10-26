package com.appspot.skillmaps.client.ui;

import com.google.gwt.user.client.ui.PopupPanel;

public class UserDialog extends PopupPanel {

    @Override
    public void center() {
        addStyleName("user-dialog");
        super.center();
    }
}
