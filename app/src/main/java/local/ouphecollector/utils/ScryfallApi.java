package local.ouphecollector.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.Card;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScryfallApi {
    private static final String TAG = "ScryfallApi";
    private final CardApiService cardApiService;

    public ScryfallApi() {
        cardApiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
    }

    public void getCardInfo(String cardName, String setCode, String collectorNumber, final CardInfoCallback callback) {
        if (cardName == null || cardName.isEmpty()) {
            callback.onCardInfoReceived(null, "Card name is empty");
            return;
        }
        String query;
        if (setCode != null && !setCode.isEmpty() && collectorNumber != null && !collectorNumber.isEmpty()) {
            query = "name:\"" + cardName + "\" set:" + setCode + " collector:" + collectorNumber;
        } else {
            query = cardName;
        }
        Call<Card> call = cardApiService.getCardByName(query);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Card> call, @NonNull Response<Card> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Card card = response.body();
                    callback.onCardInfoReceived(card, null);
                } else {
                    Log.e(TAG, "Error getting card info: " + response.message());
                    callback.onCardInfoReceived(null, "Error getting card info: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Card> call, @NonNull Throwable t) {
                Log.e(TAG, "Error getting card info: " + t.getMessage());
                callback.onCardInfoReceived(null, "Error getting card info: " + t.getMessage());
            }
        });
    }

    public interface CardInfoCallback {
        void onCardInfoReceived(Card card, String error);
    }
}