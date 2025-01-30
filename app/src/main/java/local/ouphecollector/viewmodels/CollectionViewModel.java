package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import local.ouphecollector.models.Collection;
import local.ouphecollector.repository.CollectionRepository;

import java.util.List;

public class CollectionViewModel extends AndroidViewModel {
    private CollectionRepository collectionRepository;
    private LiveData<List<Collection>> allCollections; // Changed to LiveData

    public CollectionViewModel(Application application) {
        super(application);
        collectionRepository = new CollectionRepository(application);
        allCollections = collectionRepository.getAllCollections();
    }

    public LiveData<List<Collection>> getAllCollections() {
        return allCollections;
    }

    public void insertCollection(Collection collection) {
        collectionRepository.insertCollection(collection);
    }

    public void updateCollection(Collection collection) {
        collectionRepository.updateCollection(collection);
    }

    public void deleteCollection(Collection collection) {
        collectionRepository.deleteCollection(collection);
    }
}