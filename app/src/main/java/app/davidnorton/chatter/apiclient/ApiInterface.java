package app.davidnorton.chatter.apiclient;

import app.davidnorton.chatter.ui.models.Chat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("https://api.myjson.com/bins/nmzru")
    Call<ArrayList<Chat>> getChats();
}
