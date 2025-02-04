package local.ouphecollector.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.CollectionDao;
import local.ouphecollector.models.Collection;

public class CollectionRepository {
    private CollectionDao collectionDao;
    private LiveData<List<Collection>> allCollections;

    public CollectionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        collectionDao = db.collectionDao();
        allCollections = collectionDao.getAllCollections();
    }

    public LiveData<List<Collection>> getAllCollections() {
        return allCollections;
    }

    public Collection getCollectionById(int collectionId) {
        return collectionDao.getCollectionById(collectionId);
    }

    public void insert(Collection collection) {
        AppDatabase.databaseWriteExecutor.execute(() -> collectionDao.insert(collection));
    }

    public void update(Collection collection) {
        AppDatabase.databaseWriteExecutor.execute(() -> collectionDao.update(collection));
    }

    public void delete(Collection collection) {
        AppDatabase.databaseWriteExecutor.execute(() -> collectionDao.delete(collection));
    }
}