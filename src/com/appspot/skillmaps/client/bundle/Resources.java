package com.appspot.skillmaps.client.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

    public static Resources INSTANCE = GWT.create(Resources.class);

    ImageResource noimage();

    ImageResource noimage150();

    ImageResource loader();

    Style style();

}
