package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.presenter.HomeActivity;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class HomePlace extends ActivityPlace<HomeActivity> {

    @Prefix("!home")
    public static class Tokenizer extends ActivityPlace.Tokenizer<HomePlace> implements PlaceTokenizer<HomePlace>{
    }
}
