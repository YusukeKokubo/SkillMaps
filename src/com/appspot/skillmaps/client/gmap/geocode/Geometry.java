package com.appspot.skillmaps.client.gmap.geocode;

import com.appspot.skillmaps.client.gmap.LatLngBounds;
import com.appspot.skillmaps.client.gmap.MVCObject;

public class Geometry extends MVCObject {
    protected Geometry() {
    }

    native public final LatLngBounds getViewport()/*-{
		return this.viewport;
    }-*/;

}
