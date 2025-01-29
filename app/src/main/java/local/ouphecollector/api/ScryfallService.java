package local.ouphecollector.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ScryfallService {
    @GET("cards/search")
    Call<ScryfallResponse> searchCards(@Query("q") String query);
}