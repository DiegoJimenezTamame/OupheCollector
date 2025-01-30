package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import local.ouphecollector.models.Deck;
import local.ouphecollector.repository.DeckRepository;

import java.util.List;

public class DeckViewModel extends AndroidViewModel {
    private DeckRepository deckRepository;
    private LiveData<List<Deck>> allDecks;

    public DeckViewModel(Application application) {
        super(application);
        deckRepository = new DeckRepository(application);
        allDecks = deckRepository.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() {
        return allDecks;
    }

    public void insertDeck(Deck deck) {
        deckRepository.insertDeck(deck);
    }

    public void updateDeck(Deck deck) {
        deckRepository.updateDeck(deck);
    }

    public void deleteDeck(Deck deck) {
        deckRepository.deleteDeck(deck);
    }
}