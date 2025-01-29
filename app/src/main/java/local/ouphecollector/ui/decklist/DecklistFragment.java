package local.ouphecollector.ui.decklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import local.ouphecollector.R;

public class DecklistFragment extends Fragment {
    private DecklistViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DecklistViewModel.class);
        return inflater.inflate(R.layout.fragment_decklist, container, false);
    }
}