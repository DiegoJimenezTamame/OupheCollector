package local.ouphecollector.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import local.ouphecollector.R;
import local.ouphecollector.viewmodels.ProfileViewModel;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private TextView usernameTextView;
    private TextView emailTextView;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        usernameTextView = view.findViewById(R.id.username_text_view);
        emailTextView = view.findViewById(R.id.email_text_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getAllProfiles().observe(getViewLifecycleOwner(), profiles -> {
            if (profiles != null && !profiles.isEmpty()) {
                usernameTextView.setText(profiles.get(0).getUsername());
                emailTextView.setText(profiles.get(0).getEmail());
            }
        });
    }
}