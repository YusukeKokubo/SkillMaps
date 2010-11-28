package com.appspot.skillmaps.client.presenter;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;

public abstract class SkillMapActivity extends AbstractActivity {

    protected Place place;

    public void setPlace(Place place){
        this.place = place;

    }

}
