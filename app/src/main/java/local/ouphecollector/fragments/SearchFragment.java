package local.ouphecollector.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.adapters.CardAdapter;
import local.ouphecollector.adapters.RecentSearchAdapter;
import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.Card;
import local.ouphecollector.models.CardList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements RecentSearchAdapter.OnRecentSearchClickListener {
    private static final String TAG = "SearchFragment";
    private static final int MAX_RECENT_SEARCHES = 3;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private TextView advancedSearchTextView;
    private List<Card> cards;
    private RecyclerView recentSearchesRecyclerView;
    private RecentSearchAdapter recentSearchAdapter;
    private List<String> recentSearches;
    private TextView showMoreTextView;
    private List<String> allSearches;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        advancedSearchTextView = view.findViewById(R.id.advancedSearchTextView);
        recentSearchesRecyclerView = view.findViewById(R.id.recentSearchesRecyclerView);
        showMoreTextView = view.findViewById(R.id.showMoreTextView);
        cards = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter = new CardAdapter(cards, this::onCardClicked);
        recyclerView.setAdapter(cardAdapter);

        // Load recent searches
        recentSearches = loadRecentSearches();
        allSearches = loadAllSearches();

        // Setup recent searches RecyclerView
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recentSearchesRecyclerView.setLayoutManager(horizontalLayoutManager);
        recentSearchAdapter = new RecentSearchAdapter(recentSearches, this);
        recentSearchesRecyclerView.setAdapter(recentSearchAdapter);

        setupSearchView();

        advancedSearchTextView.setOnClickListener(v -> {
            // Handle advanced search click
        });

        showMoreTextView.setOnClickListener(v -> {
            // Navigate to SearchHistoryFragment
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_nav_search_to_searchHistoryFragment);
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
                    addRecentSearch(cardName);
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

    private void addRecentSearch(String cardName) {
        recentSearches.remove(cardName);
        recentSearches.add(0, cardName);
        // Limit to MAX_RECENT_SEARCHES
        if (recentSearches.size() > MAX_RECENT_SEARCHES) {
            recentSearches.remove(MAX_RECENT_SEARCHES);
        }
        recentSearchAdapter.notifyDataSetChanged();
        saveRecentSearches(recentSearches);
        if (!allSearches.contains(cardName)) {
            allSearches.add(cardName);
            saveAllSearches(allSearches);
        }
    }

    private List<String> loadRecentSearches() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("RecentSearches", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("recentSearches", null);
        if (json == null) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        List<String> loadedSearches = gson.fromJson(json, type);
        // Limit to MAX_RECENT_SEARCHES when loading
        if (loadedSearches.size() > MAX_RECENT_SEARCHES) {
            return loadedSearches.subList(0, MAX_RECENT_SEARCHES);
        }
        return loadedSearches;
    }

    private void saveRecentSearches(List<String> recentSearches) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("RecentSearches", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recentSearches);
        editor.putString("recentSearches", json);
        editor.apply();
    }

    private void saveAllSearches(List<String> allSearches) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("RecentSearches", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(allSearches);
        editor.putString("allSearches", json);
        editor.apply();
    }

    private List<String> loadAllSearches() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("RecentSearches", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("allSearches", null);
        if (json == null) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void onRecentSearchClicked(String cardName) {
        Log.d(TAG, "onRecentSearchClicked called with cardName: " + cardName);
        // Navigate to CardDetailFragment
        Bundle bundle = new Bundle();
        bundle.putString("cardName", cardName);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_search_to_nav_cardDetail, bundle);
    }
}