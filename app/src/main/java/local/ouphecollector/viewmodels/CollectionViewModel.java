package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import local.ouphecollector.models.Collection;
import local.ouphecollector.repository.CollectionRepository;

import java.util.List;

public class CollectionViewModel extends AndroidViewModel {
    private CollectionRepository collectionRepository;
    private LiveData<List<Collection>> allCollections;

    public CollectionViewModel(Application application) {
        super(application);
        collectionRepository = new CollectionRepository(application);
        allCollections = new MutableLiveData<>();
        loadAllCollections();
    }

    public LiveData<List<Collection>> getAllCollections() {
        return allCollections;
    }

    public void loadAllCollections() {
        List<Collection> collections = collectionRepository.getAllCollections();
        ((MutableLiveData<List<Collection>>) allCollections).setValue(collections);
    }

    public void insertCollection(Collection collection) {
        collectionRepository.insertCollection(collection);
        loadAllCollections();
    }

    public void updateCollection(Collection collection) {
        collectionRepository.updateCollection(collection);
        loadAllCollections();
    }

    public void deleteCollection(Collection collection) {
        collectionRepository.deleteCollection(collection);
        loadAllCollections();
    }
}