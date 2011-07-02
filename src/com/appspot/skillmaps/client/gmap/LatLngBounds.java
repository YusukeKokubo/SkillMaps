package com.appspot.skillmaps.client.gmap;

public class LatLngBounds extends MVCObject {
    protected LatLngBounds() {
    }

    final native public LatLng getCenter()/*-{
		return this.getCenter();
    }-*/;
}
