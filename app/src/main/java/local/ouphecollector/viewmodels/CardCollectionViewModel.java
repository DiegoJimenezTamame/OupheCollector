package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import local.ouphecollector.models.CardCollection;
import local.ouphecollector.repositories.CardCollectionRepository;

public class CardCollectionViewModel extends AndroidViewModel {
    private CardCollectionRepository cardCollectionRepository;

    public CardCollectionViewModel(Application application) {
        super(application);
        cardCollectionRepository = new CardCollectionRepository(application);
    }

    public LiveData<List<CardCollection>> getCardCollectionsByCollectionId(int collectionId) {
        return cardCollectionRepository.getCardCollectionsByCollectionId(collectionId);
    }

    public LiveData<List<CardCollection>> getAllCardCollections() {
        return cardCollectionRepository.getAllCardCollections();
    }

    public CardCollection getCardCollectionById(int cardCollectionId) {
        return cardCollectionRepository.getCardCollectionById(cardCollectionId);
    }

    public List<CardCollection> getCardCollectionsByCardId(String cardId) {
        return cardCollectionRepository.getCardCollectionsByCardId(cardId);
    }

    public void insert(CardCollection cardCollection) {
        cardCollectionRepository.insert(cardCollection);
    }

    public void update(CardCollection cardCollection) {
        cardCollectionRepository.update(cardCollection);
    }

    public void delete(CardCollection cardCollection) {
        cardCollectionRepository.delete(cardCollection);
    }
}