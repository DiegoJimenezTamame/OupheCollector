package local.ouphecollector.ui.wishlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WishlistViewModel extends ViewModel {

    private final MutableLiveData<String> text;

    public WishlistViewModel() {
        text = new MutableLiveData<>();
        text.setValue("This is the Collection Fragment");
    }

    public LiveData<String> getText() {
        return text;
    }
}
