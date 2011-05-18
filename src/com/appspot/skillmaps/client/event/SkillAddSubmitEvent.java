package com.appspot.skillmaps.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SkillAddSubmitEvent extends GwtEvent<SkillAddSubmitHandler> {

    public static final Type<SkillAddSubmitHandler> TYPE = new Type<SkillAddSubmitHandler>();

    @Override
    protected void dispatch(SkillAddSubmitHandler arg0) {
        arg0.onSubmit(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SkillAddSubmitHandler> getAssociatedType() {
        return TYPE;
    }
}
