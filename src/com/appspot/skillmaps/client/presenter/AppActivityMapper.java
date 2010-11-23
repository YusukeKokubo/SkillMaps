package com.appspot.skillmaps.client.presenter;

import com.appspot.skillmaps.client.place.ActivityPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

public class AppActivityMapper implements ActivityMapper {

    @Inject
	public AppActivityMapper() {
		super();
	}

	@Override
	public Activity getActivity(Place place) {
		GWT.log(place.toString());

		if (!(place instanceof ActivityPlace<?>)) {
			return null;
		}

		ActivityPlace<?> activityPlace = (ActivityPlace<?>)place;

		return activityPlace.getActivity();
	}

}
