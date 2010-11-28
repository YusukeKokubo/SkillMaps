package com.appspot.skillmaps.client.inject;

import com.appspot.skillmaps.client.place.MyPagePlace;
import com.appspot.skillmaps.client.place.SkillOwnersPlace;
import com.appspot.skillmaps.shared.model.Login;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.Anchor;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

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

    @Provides
    @Named("activate")
    public Anchor makeSignin(final Provider<MyPagePlace> placeProvider,
                             final PlaceController placeController){
        final Anchor anchor = new Anchor("マイページへ進む");
        anchor.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                placeController.goTo(placeProvider.get());
            }
        });

        return anchor;
    }

    @Provides
    @Named("skillOwnersPermalink")
    public Anchor makePermalink(final Provider<SkillOwnersPlace> placeProvider,
                                final PlaceController placeController){

        final Anchor anchor = new Anchor("permalink");

        anchor.addClickHandler( new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                SkillOwnersPlace place = placeProvider.get();
                place.init(anchor.getName());
                placeController.goTo(place);
            }
        });
        return anchor;
    }

}
