package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.models.Card;
import local.ouphecollector.repositories.CardRepository;

public class CardViewModel extends AndroidViewModel {
    private final CardRepository cardRepository;
    private final LiveData<List<Card>> allCards;

    public CardViewModel(Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        cardRepository = new CardRepository(appDatabase);
        allCards = cardRepository.getAllCardsFromDatabase();
    }

    public LiveData<List<Card>> searchCards(String query) {
        return cardRepository.searchCards(query);
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }
}