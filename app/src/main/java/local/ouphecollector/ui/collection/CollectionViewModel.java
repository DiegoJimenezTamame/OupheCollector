package local.ouphecollector.ui.collection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CollectionViewModel extends ViewModel {

    private final MutableLiveData<String> text;

    public CollectionViewModel() {
        text = new MutableLiveData<>();
        text.setValue("This is the Collection Fragment");
    }

    public LiveData<String> getText() {
        return text;
    }
}
