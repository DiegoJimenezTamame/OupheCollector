package local.ouphecollector.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.Card;
import local.ouphecollector.models.CardList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardViewModel extends ViewModel {
    private MutableLiveData<List<Card>> allCards = new MutableLiveData<>();
    private MutableLiveData<List<Card>> searchResults = new MutableLiveData<>();
    private MutableLiveData<Boolean> searchError = new MutableLiveData<>();

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }

    public LiveData<List<Card>> getSearchResults() {
        return searchResults;
    }

    public LiveData<Boolean> getSearchError() {
        return searchError;
    }

    public void searchCards(String query) {
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<CardList> call = apiService.searchCardsByName(query);
        searchError.setValue(false);
        call.enqueue(new Callback<CardList>() {
            @Override
            public void onResponse(Call<CardList> call, Response<CardList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CardList cardList = response.body();
                    List<Card> cards = cardList.getData();
                    if (cards != null) {
                        searchResults.setValue(cards);
                    } else {
                        searchResults.setValue(new ArrayList<>()); // Empty list for no results
                    }
                } else {
                    Log.e("CardViewModel", "Error searching cards: " + response.message());
                    searchError.setValue(true);
                    searchResults.setValue(new ArrayList<>()); // Empty list for no results
                }
            }

            @Override
            public void onFailure(Call<CardList> call, Throwable t) {
                Log.e("CardViewModel", "Error searching cards", t);
                searchError.setValue(true);
                searchResults.setValue(new ArrayList<>()); // Empty list for no results
            }
        });
    }
}