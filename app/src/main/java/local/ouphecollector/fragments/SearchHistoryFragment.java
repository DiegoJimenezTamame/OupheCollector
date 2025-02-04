package local.ouphecollector.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import local.ouphecollector.adapters.RecentSearchAdapter;

public class SearchHistoryFragment extends Fragment implements RecentSearchAdapter.OnRecentSearchClickListener {

    private RecyclerView recyclerView;
    private RecentSearchAdapter adapter;
    private List<String> allSearches;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_history, container, false);
        recyclerView = view.findViewById(R.id.searchHistoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        allSearches = loadAllSearches();
        adapter = new RecentSearchAdapter(allSearches, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<String> loadAllSearches() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("RecentSearches", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("allSearches", null);
        if (json == null) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void onRecentSearchClicked(String cardName) {
        // Navigate to CardDetailFragment
        Bundle bundle = new Bundle();
        bundle.putString("cardName", cardName);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_searchHistoryFragment_to_nav_cardDetail, bundle);
    }
}