package com.appspot.skillmaps.client.place;

import com.appspot.skillmaps.client.presenter.UserListActivity;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class UserListPlace extends ActivityPlace<UserListActivity> {


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

    public UserListPlace page(int pageNumber){
        setPageNumber(pageNumber);
        return this;
    }

    public int getPageNumber(){
        return this.pageNumber;
    }

    @Prefix("!userList")
    public static class Tokenizer extends ActivityPlace.Tokenizer<UserListPlace> implements PlaceTokenizer<UserListPlace>{

    }
}
