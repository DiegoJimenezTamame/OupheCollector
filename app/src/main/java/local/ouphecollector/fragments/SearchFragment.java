package local.ouphecollector.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.adapters.CardAdapter;
import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.Card;
import local.ouphecollector.models.CardList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private SearchView searchView;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private TextView advancedSearchTextView;
    private List<Card> cards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        advancedSearchTextView = view.findViewById(R.id.advancedSearchTextView);
        cards = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter = new CardAdapter(cards, this::onCardClicked);
        recyclerView.setAdapter(cardAdapter);

        setupSearchView();

        advancedSearchTextView.setOnClickListener(v -> {
            // Handle advanced search click
        });

        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit called with query: " + query);
                fetchCards(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void fetchCards(String cardName) {
        Log.d(TAG, "fetchCards called with cardName: " + cardName);
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<CardList> call = apiService.searchCardsByName(cardName);
        call.enqueue(new Callback<>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<CardList> call, @NonNull Response<CardList> response) {
                Log.d(TAG, "fetchCards onResponse called");
                if (response.isSuccessful() && response.body() != null) {
                    cards.clear();
                    cards.addAll(response.body().getData());
                    Log.d(TAG, "fetchCards onResponse isSuccessful, cards size: " + cards.size());
                    cardAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Error fetching cards: " + response.message() + " " + response.code());
                    Toast.makeText(getContext(), "Error fetching cards", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CardList> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching cards", t);
                Toast.makeText(getContext(), "Error fetching cards", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCardClicked(Card card) {
        Log.d(TAG, "onCardClicked called with card: " + card.getName());
        // Navigate to CardDetailFragment
        Bundle bundle = new Bundle();
        bundle.putString("cardName", card.getName());
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_search_to_nav_cardDetail, bundle);
    }
}