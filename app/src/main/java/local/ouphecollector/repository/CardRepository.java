package local.ouphecollector.repository;

import android.app.Application;

import local.ouphecollector.api.ScryfallApiClient;
import local.ouphecollector.api.ScryfallResponse;
import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.CardDao;
import local.ouphecollector.models.Card;

import java.util.List;

import retrofit2.Call;

public class CardRepository {
    private CardDao cardDao;
    private ScryfallApiClient scryfallApiClient;

    public CardRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        cardDao = db.cardDao();
        scryfallApiClient = new ScryfallApiClient();
    }

    public List<Card> getAllCards() {
        return cardDao.getAllCards();
    }

    public Card getCardById(String cardId) {
        return cardDao.getCardById(cardId);
    }

    public void insertCard(Card card) {
        cardDao.insert(card);
    }

    public Call<ScryfallResponse> searchCards(String query) {
        return scryfallApiClient.searchCards(query);
    }
}