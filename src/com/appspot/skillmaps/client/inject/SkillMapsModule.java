package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.inject.client.AbstractGinModule;

public class SkillMapsModule extends AbstractGinModule {

    @Override
    protected void configure() {
        //global parameter
        bind(Login.class).asEagerSingleton();

        //Display Module
        install(new DisplayModule());

        //MVP modules
        install(new MvpModule());

    }

}
