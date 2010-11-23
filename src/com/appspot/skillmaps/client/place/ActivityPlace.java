package com.appspot.skillmaps.client.place;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class ActivityPlace<T extends Activity> extends Place {

    @Inject
    protected Provider<T> provider;

    protected String token;

    public void init(String token) {
        this.token = token != null ? token : "";
    }

    public String getToken() {
        return this.token != null ? token : "";
    }

    public T getActivity() {
        return provider.get();
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
