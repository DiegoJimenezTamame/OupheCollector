package local.ouphecollector.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.adapters.CardAdapter;
import local.ouphecollector.models.Card;
import local.ouphecollector.viewmodels.CardViewModel;

public class SearchFragment extends Fragment implements CardAdapter.CardClickListener {
    private CardViewModel cardViewModel;
    private CardAdapter cardAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView advancedSearchTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter = new CardAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(cardAdapter);

        // Initialize SearchView
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCards(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCards(newText);
                return true;
            }
        });

        // Initialize Advanced Search TextView
        advancedSearchTextView = view.findViewById(R.id.advancedSearchTextView);
        advancedSearchTextView.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Advanced Search clicked", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to advanced search fragment
        });

        // Initialize ViewModel
        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);

        // Observe all cards
        cardViewModel.getAllCards().observe(getViewLifecycleOwner(), cards -> cardAdapter.setCards(cards));

        // Observe search results
        cardViewModel.getSearchResults().observe(getViewLifecycleOwner(), cards -> {
            cardAdapter.setCards(cards);
        });

        // Observe search errors
        cardViewModel.getSearchError().observe(getViewLifecycleOwner(), error -> {
            if (error) {
                Toast.makeText(getContext(), "Error searching cards", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void searchCards(String query) {
        cardViewModel.searchCards(query);
    }

    @Override
    public void onCardClicked(Card card) {
        // Create a new instance of CardDetailFragment
        CardDetailFragment cardDetailFragment = new CardDetailFragment();

        // Create a Bundle to pass the card name
        Bundle bundle = new Bundle();
        bundle.putString("cardName", card.getName());

        // Set the arguments to the fragment
        cardDetailFragment.setArguments(bundle);

        // Get the FragmentManager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Start a FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with CardDetailFragment
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, cardDetailFragment);

        // Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }
}