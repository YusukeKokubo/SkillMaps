package com.appspot.skillmaps.client.gmap;

import com.google.gwt.dom.client.Element;

public class Maps {

    // 横浜
    static public final LatLng DEFAULT_POSITION = LatLng.create(
        35.443708,
        139.638026);

    public static GMap getOrCreateMap(Element gmap) {
        return getOrCreateMap(gmap, DEFAULT_POSITION);
    }

    public static GMap getOrCreateMap(Element gmap, LatLng center) {
        // if (gmapCache == null) {
        Options options = Options.create(9, center, MapTypeId.ROADMAP, true);
        return GMap.create(gmap, options);
    }
}
