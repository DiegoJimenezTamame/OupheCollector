package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.CardCollectionDao;
import local.ouphecollector.models.CardCollection;

public class CardCollectionViewModel extends AndroidViewModel {
    private CardCollectionDao cardCollectionDao;

    public CardCollectionViewModel(@NonNull Application application) {
        super(application);
        cardCollectionDao = AppDatabase.getDatabase(application).cardCollectionDao();
    }

    public LiveData<List<CardCollection>> getCardCollectionsByCollectionId(String collectionId) {
        return cardCollectionDao.getCardCollectionsByCollectionId(collectionId);
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