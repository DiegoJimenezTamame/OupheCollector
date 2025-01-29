package local.ouphecollector.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScryfallApiClient {
    private static final String BASE_URL = "https://api.scryfall.com/";
    private ScryfallService scryfallService;

    public ScryfallApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        scryfallService = retrofit.create(ScryfallService.class);
    }

    public Call<ScryfallResponse> searchCards(String query) {
        return scryfallService.searchCards(query);
    }
}