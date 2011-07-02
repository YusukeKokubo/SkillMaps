package com.appspot.skillmaps.client.gmap;

public class Marker extends MVCObject {
    protected Marker() {
    }

    final public native void remove()/*-{
		this.setMap(null);
    }-*/;

}
