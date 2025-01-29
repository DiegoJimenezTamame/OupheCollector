package local.ouphecollector.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import local.ouphecollector.R;
import local.ouphecollector.adapters.DeckAdapter;
import local.ouphecollector.viewmodels.DeckViewModel;

public class DeckFragment extends Fragment {
    private DeckViewModel deckViewModel;
    private RecyclerView recyclerView;
    private DeckAdapter deckAdapter;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deck, container, false);
        recyclerView = view.findViewById(R.id.deck_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deckAdapter = new DeckAdapter();
        recyclerView.setAdapter(deckAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        deckViewModel = new ViewModelProvider(this).get(DeckViewModel.class);
        deckViewModel.getAllDecks().observe(getViewLifecycleOwner(), decks -> {
            deckAdapter.setDecks(decks);
        });
    }
}