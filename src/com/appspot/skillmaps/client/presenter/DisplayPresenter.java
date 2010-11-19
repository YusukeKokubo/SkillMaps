package com.appspot.skillmaps.client.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public interface DisplayPresenter<T> {

    public void initDisplay(AcceptsOneWidget panel , EventBus eventBus);
}
