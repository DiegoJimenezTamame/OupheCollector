package local.ouphecollector.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.DeckDao;
import local.ouphecollector.models.Deck;

import java.util.List;

public class DeckRepository {
    private DeckDao deckDao;
    private LiveData<List<Deck>> allDecks;

    public DeckRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        deckDao = db.deckDao();
        allDecks = deckDao.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() {
        return allDecks; // Changed to LiveData
    }

    public Deck getDeckById(int deckId) {
        return deckDao.getDeckById(deckId);
    }

    public void insertDeck(Deck deck) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            deckDao.insert(deck);
        });
    }

    public void updateDeck(Deck deck) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            deckDao.update(deck);
        });
    }

    public void deleteDeck(Deck deck) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            deckDao.delete(deck);
        });
    }
}