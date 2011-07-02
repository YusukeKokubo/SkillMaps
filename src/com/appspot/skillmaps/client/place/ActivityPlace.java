package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.presenter.ActivityProxy;
import com.appspot.skillmaps.client.presenter.SkillMapActivity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class ActivityPlace<T extends SkillMapActivity> extends Place {

    @Inject
    protected Provider<ActivityProxy<T>> provider;
    protected String token;

    public void init(String token) {
        this.token = token != null ? token : "";
    }

    public String getToken() {
        return this.token != null ? token : "";
    }

    public ActivityProxy<T> getActivity() {
        ActivityProxy<T> t = provider.get();
        t.setPlace(this);
        return t;
    }

    abstract public static class Tokenizer<E extends ActivityPlace<?>>
            implements PlaceTokenizer<E> {
        @Inject
        Provider<E> provider;

        @Override
        public E getPlace(String token) {
            E place = provider.get();
            place.init(token);
            return place;
        }

        @Override
        public String getToken(E place) {
            return place.getToken();
        }
    }
}
