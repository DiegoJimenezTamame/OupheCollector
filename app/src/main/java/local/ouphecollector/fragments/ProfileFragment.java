package local.ouphecollector.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import local.ouphecollector.R;
import local.ouphecollector.models.Profile;
import local.ouphecollector.viewmodels.ProfileViewModel;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private TextView usernameTextView;
    private TextView emailTextView;
    private EditText newUsernameEditText;
    private EditText newEmailEditText;
    private Button createProfileButton;
    private TextView profileCreatedMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        usernameTextView = view.findViewById(R.id.username_text_view);
        emailTextView = view.findViewById(R.id.email_text_view);
        newUsernameEditText = view.findViewById(R.id.new_username_edit_text);
        newEmailEditText = view.findViewById(R.id.new_email_edit_text);
        createProfileButton = view.findViewById(R.id.create_profile_button);
        profileCreatedMessage = view.findViewById(R.id.profile_created_message);
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
            } else {
                usernameTextView.setText("");
                emailTextView.setText("");
            }
        });

        createProfileButton.setOnClickListener(v -> {
            String newUsername = newUsernameEditText.getText().toString().trim();
            String newEmail = newEmailEditText.getText().toString().trim();

            if (newUsername.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Profile newProfile = new Profile(newUsername, newEmail);
            profileViewModel.insertProfile(newProfile);

            // Clear the input fields
            newUsernameEditText.setText("");
            newEmailEditText.setText("");

            // Show the success message
            profileCreatedMessage.setText(getString(R.string.profile_created));
            profileCreatedMessage.setVisibility(View.VISIBLE);
        });
    }
}