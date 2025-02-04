package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import local.ouphecollector.models.Collection;
import local.ouphecollector.repositories.CollectionRepository;

public class CollectionViewModel extends AndroidViewModel {
    private CollectionRepository collectionRepository;
    private LiveData<List<Collection>> allCollections;

    public CollectionViewModel(Application application) {
        super(application);
        collectionRepository = new CollectionRepository(application);
        allCollections = collectionRepository.getAllCollections();
    }

    public LiveData<List<Collection>> getAllCollections() {
        return allCollections;
    }

    public Collection getCollectionById(int collectionId) {
        return collectionRepository.getCollectionById(collectionId);
    }

    public void insert(Collection collection) {
        collectionRepository.insert(collection);
    }

    public void update(Collection collection) {
        collectionRepository.update(collection);
    }

    public void delete(Collection collection) {
        collectionRepository.delete(collection);
    }
}