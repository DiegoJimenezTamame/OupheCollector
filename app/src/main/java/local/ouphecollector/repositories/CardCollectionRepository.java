package local.ouphecollector.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.CardCollectionDao;
import local.ouphecollector.models.CardCollection;

public class CardCollectionRepository {
    private CardCollectionDao cardCollectionDao;

    public CardCollectionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        cardCollectionDao = db.cardCollectionDao();
    }

    public LiveData<List<CardCollection>> getCardCollectionsByCollectionId(int collectionId) {
        return cardCollectionDao.getCardCollectionsByCollectionId(collectionId);
    }

    public LiveData<List<CardCollection>> getAllCardCollections() {
        return cardCollectionDao.getAllCardCollections();
    }

    public CardCollection getCardCollectionById(int cardCollectionId) {
        return cardCollectionDao.getCardCollectionById(cardCollectionId);
    }

    public List<CardCollection> getCardCollectionsByCardId(String cardId) {
        return cardCollectionDao.getCardCollectionsByCardId(cardId);
    }

    public void insert(CardCollection cardCollection) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardCollectionDao.insert(cardCollection));
    }

    public void update(CardCollection cardCollection) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardCollectionDao.update(cardCollection));
    }

    public void delete(CardCollection cardCollection) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardCollectionDao.delete(cardCollection));
    }
}