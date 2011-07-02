package com.appspot.skillmaps.client.gmap;

public class MapTypeId {
	static final public String ROADMAP = ROADMAP();
	static final public String SATELLITE = SATELLITE();
	static final public String HYBRID = HYBRID();
	static final public String TERRAIN = TERRAIN();

	static native String ROADMAP()/*-{
		return $wnd.google.maps.MapTypeId.ROADMAP;
	}-*/;

	static native String SATELLITE()/*-{
		return $wnd.google.maps.MapTypeId.SATELLITE;
	}-*/;

	static native String HYBRID()/*-{
		return $wnd.google.maps.MapTypeId.HYBRID;
	}-*/;

	static native String TERRAIN()/*-{
		return $wnd.google.maps.MapTypeId.TERRAIN;
	}-*/;

}
