package local.ouphecollector.api;

import local.ouphecollector.models.Card;
import local.ouphecollector.models.CardList;
import local.ouphecollector.models.CardSymbolList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface CardApiService {
    @GET("cards/named")
    Call<Card> getCardByName(@Query("exact") String cardName);

    @GET("cards/search")
    Call<CardList> searchCardsByName(@Query("q") String cardName);

    @GET("symbology")
    Call<CardSymbolList> getCardSymbols();

    @GET
    Call<ResponseBody> getSvg(@Url String url);

    @GET
    Call<CardList> getCardPrintings(@Url String url);
}