package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.presenter.SkillAssertionUIActivity;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SkillAssertionPlace extends ActivityPlace<SkillAssertionUIActivity> {

    private String assertionKey;

    @Override
    public void init(String token) {
        assertionKey = token;
        super.init(token);
    }

    public void setAssertionKey(String assertionKey){
        init(assertionKey);
    }

    public SkillAssertionPlace key(String assertionKey){
        setAssertionKey(assertionKey);
        return this;
    }

    public String getAssertionKey(){
        return this.assertionKey;
    }

    @Prefix("!assertion")
    public static class Tokenizer extends ActivityPlace.Tokenizer<SkillAssertionPlace> implements PlaceTokenizer<SkillAssertionPlace>{
    }
}
