package local.ouphecollector.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.adapters.CardPrintingAdapter;
import local.ouphecollector.models.Card;

public class AllPrintingsFragment extends Fragment implements CardPrintingAdapter.OnCardClickListener {

    private RecyclerView allPrintingsRecyclerView;
    private CardPrintingAdapter adapter;
    private ProgressBar progressBar;
    private TextView errorMessageTextView;
    private static final String TAG = "AllPrintingsFragment";
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_printings, container, false);

        allPrintingsRecyclerView = view.findViewById(R.id.allPrintingsRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        errorMessageTextView = view.findViewById(R.id.errorMessageTextView);
        toolbar = view.findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        allPrintingsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new CardPrintingAdapter(new ArrayList<>(), this);
        allPrintingsRecyclerView.setAdapter(adapter);

        showLoading();
        if (getArguments() != null && getArguments().containsKey("allPrintings")) {
            List<Card> allPrintings = getArguments().getParcelableArrayList("allPrintings");
            if (allPrintings != null && !allPrintings.isEmpty()) {
                adapter.updateData(allPrintings);
                hideLoading();
            } else {
                showErrorMessage(getString(R.string.no_results_found));
            }
        } else {
            showErrorMessage(getString(R.string.error_loading_data));
        }

        return view;
    }

    private void showLoading() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (errorMessageTextView != null) {
            errorMessageTextView.setVisibility(View.GONE);
        }
        if (allPrintingsRecyclerView != null) {
            allPrintingsRecyclerView.setVisibility(View.GONE);
        }
    }

    private void hideLoading() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (allPrintingsRecyclerView != null) {
            allPrintingsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showErrorMessage(String message) {
        if (errorMessageTextView != null) {
            errorMessageTextView.setText(message);
            errorMessageTextView.setVisibility(View.VISIBLE);
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (allPrintingsRecyclerView != null) {
            allPrintingsRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCardClick(Card card) {
        Log.d(TAG, "onCardClick: Card clicked: " + card.getName());
        Bundle bundle = new Bundle();
        bundle.putParcelable("card", card);
        Navigation.findNavController(requireView()).navigate(R.id.action_allPrintingsFragment_to_cardDetailFragment, bundle);
    }
}