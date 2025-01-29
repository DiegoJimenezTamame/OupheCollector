package local.ouphecollector.ui.decklist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DecklistViewModel extends ViewModel {

    private final MutableLiveData<String> text;

    public DecklistViewModel() {
        text = new MutableLiveData<>();
        text.setValue("This is the Collection Fragment");
    }

    public LiveData<String> getText() {
        return text;
    }
}
