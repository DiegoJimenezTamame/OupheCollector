package local.ouphecollector.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.scryfall.com/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Create a logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY); // Set the desired log level

            // Add the interceptor to an OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            // Create a Gson instance with lenient parsing
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // Set the OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Set the Gson instance
                    .build();
        }
        return retrofit;
    }
}