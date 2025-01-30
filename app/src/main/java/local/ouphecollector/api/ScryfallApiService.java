package local.ouphecollector.api;

import local.ouphecollector.models.CardList;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ScryfallApiService {
    @GET("cards/search")
    Call<CardList> searchCards(@Query("q") String query);

    @Multipart
    @POST("cards/image")
    Call<CardList> searchCardsByImage(@Part MultipartBody.Part image);
}