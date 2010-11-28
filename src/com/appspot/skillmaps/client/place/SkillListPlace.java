package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.presenter.SkillListActivity;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SkillListPlace extends ActivityPlace<SkillListActivity> {

    @Prefix("!skillList")
    public static class Tokenizer extends ActivityPlace.Tokenizer<SkillListPlace> implements PlaceTokenizer<SkillListPlace>{

    }
}
