package local.ouphecollector.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import local.ouphecollector.api.ScryfallApiService;
import local.ouphecollector.api.ScryfallRetrofit;
import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.CardDao;
import local.ouphecollector.models.Card;
import local.ouphecollector.models.CardList;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardRepository {
    private final ScryfallApiService scryfallApiService;
    private final CardDao cardDao;

    public CardRepository(AppDatabase appDatabase) {
        ScryfallRetrofit scryfallRetrofit = new ScryfallRetrofit();
        this.scryfallApiService = scryfallRetrofit.getScryfallApiService();
        this.cardDao = appDatabase.cardDao();
    }

    public LiveData<List<Card>> searchCards(String query) {
        MutableLiveData<List<Card>> data = new MutableLiveData<>();
        scryfallApiService.searchCards(query).enqueue(new Callback<CardList>() {
            @Override
            public void onResponse(Call<CardList> call, Response<CardList> response) {
                if (response.isSuccessful()) {
                    CardList cardList = response.body();
                    if (cardList != null) {
                        data.setValue(cardList.getCards());
                    }
                } else {
                    Log.e("CardRepository", "Error searching cards: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CardList> call, Throwable t) {
                Log.e("CardRepository", "Error searching cards", t);
            }
        });
        return data;
    }

    public LiveData<List<Card>> searchCardsByImage(MultipartBody.Part image) {
        MutableLiveData<List<Card>> data = new MutableLiveData<>();
        scryfallApiService.searchCardsByImage(image).enqueue(new Callback<CardList>() {
            @Override
            public void onResponse(Call<CardList> call, Response<CardList> response) {
                if (response.isSuccessful()) {
                    CardList cardList = response.body();
                    if (cardList != null) {
                        data.setValue(cardList.getCards());
                    }
                } else {
                    Log.e("CardRepository", "Error searching cards by image: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CardList> call, Throwable t) {
                Log.e("CardRepository", "Error searching cards by image", t);
            }
        });
        return data;
    }

    public void insertCard(Card card) {
        // Insert the card in the database
        cardDao.insert(card);
    }

    public LiveData<List<Card>> getAllCardsFromDatabase() {
        return cardDao.getAllCards();
    }
}