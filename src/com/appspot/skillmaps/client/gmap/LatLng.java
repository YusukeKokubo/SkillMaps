package com.appspot.skillmaps.client.gmap;

import com.google.gwt.dom.client.Element;

public class LatLng extends Element {
	protected LatLng() {
	}

	static public native LatLng create(double lat, double lng)/*-{
		return new $wnd.google.maps.LatLng(lat, lng);
	}-*/;

	final native public double getLat()/*-{
		return this.lat();
	}-*/;

	final public native double getLng() /*-{
		return this.lng();
	}-*/;

}
