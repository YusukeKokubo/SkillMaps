package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.inject.TokenizerFactory;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Inject;

public class InjectablePlaceHistoryHandler extends PlaceHistoryHandler {

	@Inject
	public InjectablePlaceHistoryHandler(AppPlaceHistoryMapper mapper
										,PlaceController placeController
										,EventBus eventBus
										,HomePlace defaultPlace
										, TokenizerFactory injector) {
		super(mapper);
		mapper.setFactory(injector);
		register(placeController, eventBus, defaultPlace);
	}

}
