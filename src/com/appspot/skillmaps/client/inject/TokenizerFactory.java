package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.client.place.HomePlace;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(SkillMapsModule.class)
public interface TokenizerFactory extends Ginjector {

    HomePlace.Tokenizer getHomePlaceTokenizer();
}
