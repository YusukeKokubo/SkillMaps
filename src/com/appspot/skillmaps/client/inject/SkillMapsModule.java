package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class SkillMapsModule extends AbstractGinModule {

    @Override
    protected void configure() {
        //global parameter
        bind(Login.class).in(Singleton.class);

        //Display Module
        install(new DisplayModule());

        //MVP modules
        install(new MvpModule());

    }

}
