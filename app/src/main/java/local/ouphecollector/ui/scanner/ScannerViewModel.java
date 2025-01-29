package local.ouphecollector.ui.scanner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScannerViewModel extends ViewModel {

    private final MutableLiveData<String> text;

    public ScannerViewModel() {
        text = new MutableLiveData<>();
        text.setValue("This is the Collection Fragment");
    }

    public LiveData<String> getText() {
        return text;
    }
}
