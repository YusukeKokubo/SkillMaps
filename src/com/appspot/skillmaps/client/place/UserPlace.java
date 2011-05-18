package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.presenter.UserUIActivity;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class UserPlace extends ActivityPlace<UserUIActivity> {

    private String userid;

    @Override
    public void init(String token) {
        userid = token;
        super.init(token);
    }

    public void setUserId(String userid){
        init(userid);
    }

    public UserPlace user(String userid){
        setUserId(userid);
        return this;
    }

    public String getUserId(){
        return this.userid;
    }

    @Prefix("!user")
    public static class Tokenizer extends ActivityPlace.Tokenizer<UserPlace> implements PlaceTokenizer<UserPlace>{
    }
}
