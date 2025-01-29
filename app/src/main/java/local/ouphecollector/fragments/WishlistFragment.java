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
import local.ouphecollector.adapters.WishlistAdapter;
import local.ouphecollector.viewmodels.WishlistViewModel;

public class WishlistFragment extends Fragment {
    private WishlistViewModel wishlistViewModel;
    private RecyclerView recyclerView;
    private WishlistAdapter wishlistAdapter;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        recyclerView = view.findViewById(R.id.wishlist_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wishlistAdapter = new WishlistAdapter();
        recyclerView.setAdapter(wishlistAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wishlistViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);
        wishlistViewModel.getAllWishlists().observe(getViewLifecycleOwner(), wishlists -> {
            wishlistAdapter.setWishlists(wishlists);
        });
    }
}