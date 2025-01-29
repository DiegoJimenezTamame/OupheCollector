package local.ouphecollector.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> text;

    public ProfileViewModel() {
        text = new MutableLiveData<>();
        text.setValue("This is the Collection Fragment");
    }

    public LiveData<String> getText() {
        return text;
    }
}
