package local.ouphecollector.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.CollectionDao;
import local.ouphecollector.models.Collection;

import java.util.List;

public class CollectionRepository {
    private CollectionDao collectionDao;
    private LiveData<List<Collection>> allCollections;

    public CollectionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        collectionDao = db.collectionDao();
        allCollections = collectionDao.getAllCollections();
    }

    public LiveData<List<Collection>> getAllCollections() {
        return allCollections; // Changed to LiveData
    }

    public Collection getCollectionById(int collectionId) {
        return collectionDao.getCollectionById(collectionId);
    }

    public List<Collection> getCollectionsByCardId(String cardId) {
        return collectionDao.getCollectionsByCardId(cardId);
    }

    public void insertCollection(Collection collection) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            collectionDao.insert(collection);
        });
    }

    public void updateCollection(Collection collection) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            collectionDao.update(collection);
        });
    }

    public void deleteCollection(Collection collection) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            collectionDao.delete(collection);
        });
    }
}