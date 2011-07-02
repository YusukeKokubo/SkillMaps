package com.appspot.skillmaps.client.gmap.geocode;

import com.google.gwt.core.client.JsArray;

public abstract class GeocodeEventFunction {

    protected abstract void f(JsArray<GeocoderResult> results, String status);
}
