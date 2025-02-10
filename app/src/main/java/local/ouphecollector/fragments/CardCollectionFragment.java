package local.ouphecollector.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import local.ouphecollector.R;
import local.ouphecollector.adapters.CardCollectionAdapter;
import local.ouphecollector.models.CardCollection;
import local.ouphecollector.viewmodels.CardCollectionViewModel;

public class CardCollectionFragment extends Fragment {
    private static final String TAG = "CardCollectionFragment";
    private CardCollectionAdapter cardCollectionAdapter;
    private CardCollectionViewModel cardCollectionViewModel;
    private String collectionId;
    private FloatingActionButton fabAddCardCollection;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_collection, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.card_collection_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardCollectionAdapter = new CardCollectionAdapter();
        recyclerView.setAdapter(cardCollectionAdapter);

        fabAddCardCollection = view.findViewById(R.id.fab_add_card_collection);
        fabAddCardCollection.setOnClickListener(v -> showAddCardCollectionDialog());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardCollectionViewModel = new ViewModelProvider(this).get(CardCollectionViewModel.class);
        // Get the collectionId from the arguments
        if (getArguments() != null) {
            collectionId = getArguments().getString("collectionId");
        }
        // Use the String collectionId here
        cardCollectionViewModel.getCardCollectionsByCollectionId(collectionId)
                .observe(getViewLifecycleOwner(), cardCollections -> cardCollectionAdapter.setCardCollections(cardCollections));
    }

    private void showAddCardCollectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_collection, null);

        builder.setView(dialogView)
                .setTitle(R.string.add_card_collection)
                .setPositiveButton(R.string.add, (dialog, id) -> {
                    TextInputLayout cardIdLayout = dialogView.findViewById(R.id.card_id_layout);
                    EditText cardIdEditText = dialogView.findViewById(R.id.card_id_edit_text);
                    TextInputLayout quantityLayout = dialogView.findViewById(R.id.quantity_layout);
                    EditText quantityEditText = dialogView.findViewById(R.id.quantity_edit_text);
                    Spinner conditionSpinner = dialogView.findViewById(R.id.condition_spinner);
                    CheckBox isFoilCheckBox = dialogView.findViewById(R.id.is_foil_checkbox);
                    TextInputLayout languageLayout = dialogView.findViewById(R.id.language_layout);
                    EditText languageEditText = dialogView.findViewById(R.id.language_edit_text);
                    TextInputLayout setCodeLayout = dialogView.findViewById(R.id.set_code_layout);
                    EditText setCodeEditText = dialogView.findViewById(R.id.set_code_edit_text);

                    String cardId = cardIdEditText.getText().toString();
                    // Handle potential NumberFormatException
                    int quantity;
                    try {
                        quantity = Integer.parseInt(quantityEditText.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
                        return; // Exit the method if quantity is invalid
                    }
                    String condition = conditionSpinner.getSelectedItem().toString();
                    boolean isFoil = isFoilCheckBox.isChecked();
                    String language = languageEditText.getText().toString();
                    String setCode = setCodeEditText.getText().toString();

                    Log.d(TAG, "Adding card collection with cardId: " + cardId + ", quantity: " + quantity + ", condition: " + condition + ", isFoil: " + isFoil + ", language: " + language + ", setCode: " + setCode);
                    addNewCardCollection(cardId, quantity, condition, isFoil, language, setCode);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

        Spinner conditionSpinner = dialogView.findViewById(R.id.condition_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.card_conditions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(adapter);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addNewCardCollection(String cardId, int quantity, String condition, boolean isFoil, String language, String setCode) {
        CardCollection newCardCollection = new CardCollection(collectionId, cardId, quantity, condition, isFoil, language, setCode);
        cardCollectionViewModel.insert(newCardCollection);
        Toast.makeText(getContext(), "Card Collection added", Toast.LENGTH_SHORT).show();
    }
}