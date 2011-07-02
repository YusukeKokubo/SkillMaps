package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.place.ActivityPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class ActivityProxy<A extends SkillMapActivity> implements Activity {

	private final AsyncProvider<A> activityProvider;
	private A activity;
	private boolean isCancelled;
    private ActivityPlace<A> place;

	@Inject
	public ActivityProxy(AsyncProvider<A> activityProvider) {
		this.activityProvider = activityProvider;
	}

	@Override
	public String mayStop() {
		if (activity != null) {
			return activity.mayStop();
		}
		return null;
	}

	@Override
	public final void onCancel() {
		isCancelled = true;
		if (activity != null) {
			activity.onCancel();
		}
	}

	@Override
	public void onStop() {
		if (activity != null) {
			activity.onStop();
		}
	}

	@Override
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

		// if activity already there start it...
		if (activity != null) {

		    activity.setPlace(place);
			activity.start(panel, eventBus);

			// otherwise load it...
		} else {

			activityProvider.get(new AsyncCallback<A>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO or FIXME if you want me to do anything
				}

				@Override
				public void onSuccess(A result) {

					activity = result;
					activity.setPlace(place);
					if (!isCancelled) {
						activity.start(panel, eventBus);
					}
				}

			});
		}
	}

	public void setPlace(ActivityPlace<A> place) {
        this.place = place;
	}

}
