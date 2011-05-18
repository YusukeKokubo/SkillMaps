package com.appspot.skillmaps.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SkillCommentAddSubmitEvent extends GwtEvent<SkillCommentAddSubmitHandler> {

    public static final Type<SkillCommentAddSubmitHandler> TYPE = new Type<SkillCommentAddSubmitHandler>();

    @Override
    protected void dispatch(SkillCommentAddSubmitHandler arg0) {
        arg0.onSubmit(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SkillCommentAddSubmitHandler> getAssociatedType() {
        return TYPE;
    }
}
