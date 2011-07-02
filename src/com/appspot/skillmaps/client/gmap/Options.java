package com.appspot.skillmaps.client.gmap;

import com.google.gwt.dom.client.Element;

public class Options extends Element {
	protected Options() {
	}

	static public native Options create(int zoom, LatLng center,
			String mapTypeId,boolean disableDefaultUI)/*-{
		return {
			"zoom" : zoom,
			"center" : center,
			"mapTypeId" : mapTypeId,
			"disableDefaultUI":disableDefaultUI
		};
	}-*/;

}
