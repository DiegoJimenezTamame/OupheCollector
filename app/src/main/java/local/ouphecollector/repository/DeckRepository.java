package local.ouphecollector.repository;

import android.app.Application;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.DeckDao;
import local.ouphecollector.models.Deck;

import java.util.List;

public class DeckRepository {
    private DeckDao deckDao;

    public DeckRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        deckDao = db.deckDao();
    }

    public List<Deck> getAllDecks() {
        return deckDao.getAllDecks();
    }

    public Deck getDeckById(int deckId) {
        return deckDao.getDeckById(deckId);
    }

    public void insertDeck(Deck deck) {
        deckDao.insert(deck);
    }

    public void updateDeck(Deck deck) {
        deckDao.update(deck);
    }

    public void deleteDeck(Deck deck) {
        deckDao.delete(deck);
    }
}