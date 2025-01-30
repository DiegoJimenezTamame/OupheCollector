package local.ouphecollector.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScryfallRetrofit {
    private static final String BASE_URL = "https://api.scryfall.com/";
    private final ScryfallApiService scryfallApiService;

    public ScryfallRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        scryfallApiService = retrofit.create(ScryfallApiService.class);
    }

    public ScryfallApiService getScryfallApiService() {
        return scryfallApiService;
    }
}