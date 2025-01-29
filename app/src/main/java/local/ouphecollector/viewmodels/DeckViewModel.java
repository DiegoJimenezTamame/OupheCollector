package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import local.ouphecollector.models.Deck;
import local.ouphecollector.repository.DeckRepository;

import java.util.List;

public class DeckViewModel extends AndroidViewModel {
    private DeckRepository deckRepository;
    private LiveData<List<Deck>> allDecks;

    public DeckViewModel(Application application) {
        super(application);
        deckRepository = new DeckRepository(application);
        allDecks = new MutableLiveData<>();
        loadAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() {
        return allDecks;
    }

    public void loadAllDecks() {
        List<Deck> decks = deckRepository.getAllDecks();
        ((MutableLiveData<List<Deck>>) allDecks).setValue(decks);
    }

    public void insertDeck(Deck deck) {
        deckRepository.insertDeck(deck);
        loadAllDecks();
    }

    public void updateDeck(Deck deck) {
        deckRepository.updateDeck(deck);
        loadAllDecks();
    }

    public void deleteDeck(Deck deck) {
        deckRepository.deleteDeck(deck);
        loadAllDecks();
    }
}