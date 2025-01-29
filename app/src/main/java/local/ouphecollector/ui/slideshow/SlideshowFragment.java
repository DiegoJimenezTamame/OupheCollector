package local.ouphecollector.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import local.ouphecollector.R;

public class SlideshowFragment extends Fragment {
    private SlideshowViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        return inflater.inflate(R.layout.fragment_collection, container, false);
    }
}