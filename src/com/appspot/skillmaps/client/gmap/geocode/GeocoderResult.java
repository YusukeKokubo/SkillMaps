package com.appspot.skillmaps.client.gmap.geocode;

import com.appspot.skillmaps.client.gmap.MVCObject;

public class GeocoderResult extends MVCObject {
    protected GeocoderResult() {
    }

    native final public String getFormattedAddress()/*-{
		return this.formatted_address;
    }-*/;

    native final public Geometry getGeometry()/*-{
		return this.geometry;
    }-*/;

}
