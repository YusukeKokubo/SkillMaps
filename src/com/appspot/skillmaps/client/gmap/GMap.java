package com.appspot.skillmaps.client.gmap;

import com.google.gwt.dom.client.Element;

public class GMap extends MVCObject {

    protected GMap() {
    }

    static native public GMap create(Element element, Options options)/*-{
		var m = new $wnd.google.maps.Map(element, options);
		return m;
    }-*/;

    static native public GMap create(String id, Options options)/*-{
		var m = new $wnd.google.maps.Map($wnd.document.getElementById(id),
				options);
		return m;
    }-*/;

    final native public void setCenter(LatLng center)/*-{
		this.setCenter(center);
    }-*/;

    final public native Marker createMarker(LatLng position, String title)/*-{
		var marker = new $wnd.google.maps.Marker({
			position : position,
			map : this,
			title : title
		});
		return marker;
    }-*/;

}
