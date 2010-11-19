package com.appspot.skillmaps.client.presenter;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;

public class InjectableActivityManager extends ActivityManager {

	private AcceptsOneWidget widget;

	@Inject
	public InjectableActivityManager(AppActivityMapper mapper, EventBus eventBus) {
		super(mapper, eventBus);
		SimplePanel contentsPanel = new SimplePanel();
		setDisplay(contentsPanel);
		RootPanel.get("contents").add(contentsPanel);
	}

	@Override
	public void setDisplay(AcceptsOneWidget widget){
		super.setDisplay(widget);
		this.widget = widget;
	}

	public AcceptsOneWidget getDisplay(){
		return this.widget;
	}

}
