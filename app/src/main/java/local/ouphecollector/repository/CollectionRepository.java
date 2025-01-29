package local.ouphecollector.repository;

import android.app.Application;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.CollectionDao;
import local.ouphecollector.models.Collection;

import java.util.List;

public class CollectionRepository {
    private CollectionDao collectionDao;

    public CollectionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        collectionDao = db.collectionDao();
    }

    public List<Collection> getAllCollections() {
        return collectionDao.getAllCollections();
    }

    public Collection getCollectionById(int collectionId) {
        return collectionDao.getCollectionById(collectionId);
    }

    public List<Collection> getCollectionsByCardId(String cardId) {
        return collectionDao.getCollectionsByCardId(cardId);
    }

    public void insertCollection(Collection collection) {
        collectionDao.insert(collection);
    }

    public void updateCollection(Collection collection) {
        collectionDao.update(collection);
    }

    public void deleteCollection(Collection collection) {
        collectionDao.delete(collection);
    }
}