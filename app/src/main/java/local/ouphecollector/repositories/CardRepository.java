package local.ouphecollector.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.CardDao;
import local.ouphecollector.database.dao.CardListDao;
import local.ouphecollector.models.Card;
import local.ouphecollector.models.CardList;
import local.ouphecollector.models.CardListEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardRepository {
    private final CardDao cardDao;
    private final CardListDao cardListDao;
    private final CardApiService cardApiService;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CardRepository(AppDatabase appDatabase) {
        cardDao = appDatabase.cardDao();
        cardListDao = appDatabase.cardListDao();
        cardApiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
    }

    public LiveData<List<Card>> getAllCardsFromDatabase() {
        return cardDao.getAllCards();
    }

    public LiveData<List<Card>> searchCards(String query) {
        MutableLiveData<List<Card>> searchResults = new MutableLiveData<>();
        Call<CardList> call = cardApiService.searchCardsByName(query); // Use the new method

        call.enqueue(new Callback<CardList>() {

            @Override
            public void onResponse(@NonNull Call<CardList> call, Response<CardList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CardList cardList = response.body();
                    List<Card> cards = cardList.getData();
                    searchResults.setValue(cards);

                    // Store the card list in the database
                    executor.execute(() -> {
                        String cardIds = cards.stream()
                                .map(card -> String.valueOf(card.getId())) // Corrected line
                                .collect(Collectors.joining(","));
                        CardListEntity cardListEntity = new CardListEntity(cardIds);
                        cardListDao.insert(cardListEntity);
                    });
                } else {
                    Log.e("CardRepository", "Error searching cards: " + response.message());
                    searchResults.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<CardList> call, Throwable t) {
                Log.e("CardRepository", "Error searching cards", t);
                searchResults.setValue(new ArrayList<>());
            }
        });

        return searchResults;
    }

    public Card getCardById(String cardId) {
        return cardDao.getCardById(cardId);
    }
}