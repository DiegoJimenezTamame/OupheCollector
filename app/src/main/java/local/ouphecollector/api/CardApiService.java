package local.ouphecollector.api;

import local.ouphecollector.models.Card;
import local.ouphecollector.models.CardList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CardApiService {
    @GET("cards/named") // Corrected URL path
    Call<Card> getCardByName(@Query("fuzzy") String name); // Corrected query parameter

    @GET("cards/search") // New method to search for cards
    Call<CardList> searchCardsByName(@Query("q") String query);
}