package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.presenter.SkillOwnersActivity;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SkillOwnersPlace extends ActivityPlace<SkillOwnersActivity> {

    public String getSkillName(){
        return this.token;
    }

    @Prefix("!skill")
    public static class Tokenizer extends ActivityPlace.Tokenizer<SkillOwnersPlace> implements PlaceTokenizer<SkillOwnersPlace>{

    }

}
