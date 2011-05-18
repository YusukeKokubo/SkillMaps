package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.presenter.FriendsActivity;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class FriendsPlace extends ActivityPlace<FriendsActivity> {

    private int pageNumber;

    @Override
    public void init(String token) {
        pageNumber = 0;
        try{
            pageNumber = Integer.parseInt(token);
        }catch(Exception e){

        }
        token = String.valueOf(pageNumber);
        super.init(token);
    }

    public void setPageNumber(int pageNumber){
        init(String.valueOf(pageNumber));
    }

    public FriendsPlace page(int pageNumber){
        setPageNumber(pageNumber);
        return this;
    }

    public int getPageNumber(){
        return this.pageNumber;
    }

    @Prefix("!friends")
    public static class Tokenizer extends ActivityPlace.Tokenizer<FriendsPlace> implements PlaceTokenizer<FriendsPlace>{
    }
}
