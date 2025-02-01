package local.ouphecollector.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import androidx.appcompat.widget.SearchView;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private androidx.appcompat.widget.SearchView searchView;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private TextView advancedSearchTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        advancedSearchTextView = view.findViewById(R.id.advancedSearchTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter = new CardAdapter(new ArrayList<>(), this::onCardClicked);
        recyclerView.setAdapter(cardAdapter);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchCards(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        advancedSearchTextView.setOnClickListener(v -> {
            // Handle advanced search click
        });

        return view;
    }

    private void fetchCards(String cardName) {
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<CardList> call = apiService.searchCardsByName(cardName); // Corrected method name
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CardList> call, @NonNull Response<CardList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Card> cards = response.body().getData(); // Corrected method name
                    cardAdapter.setCards(cards); // Corrected method name
                } else {
                    Log.e(TAG, "Error fetching cards: " + response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CardList> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching cards", t);
            }
        });
    }

    private void onCardClicked(Card card) {
        // Navigate to CardDetailFragment
        Bundle bundle = new Bundle();
        bundle.putString("cardName", card.getName());
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_search_to_nav_cardDetail, bundle);
    }
}