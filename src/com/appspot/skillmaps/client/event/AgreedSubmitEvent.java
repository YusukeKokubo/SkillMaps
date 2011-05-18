package com.appspot.skillmaps.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AgreedSubmitEvent extends GwtEvent<AgreedSubmitHandler> {

    public static final Type<AgreedSubmitHandler> TYPE = new Type<AgreedSubmitHandler>();

    @Override
    protected void dispatch(AgreedSubmitHandler arg0) {
        arg0.onSubmit(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<AgreedSubmitHandler> getAssociatedType() {
        return TYPE;
    }
}
