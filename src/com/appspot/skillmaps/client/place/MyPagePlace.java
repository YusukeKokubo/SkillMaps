package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.presenter.MyPageActivity;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class MyPagePlace extends ActivityPlace<MyPageActivity> {

    @Prefix("!myPage")
    public static class Tokenizer extends ActivityPlace.Tokenizer<MyPagePlace> implements PlaceTokenizer<MyPagePlace>{
    }
}
