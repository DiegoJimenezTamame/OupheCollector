package local.ouphecollector.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import local.ouphecollector.R;
import local.ouphecollector.adapters.CollectionAdapter;
import local.ouphecollector.models.Collection;
import local.ouphecollector.viewmodels.CollectionViewModel;

public class CollectionFragment extends Fragment implements CollectionAdapter.OnItemClickListener, CollectionAdapter.OnItemLongClickListener {
    private static final String TAG = "CollectionFragment";
    private CollectionAdapter collectionAdapter;
    private FloatingActionButton fabAddCollection;
    private CollectionViewModel collectionViewModel;
    private NavController navController;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.collection_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        collectionAdapter = new CollectionAdapter();
        recyclerView.setAdapter(collectionAdapter);
        collectionAdapter.setOnItemClickListener(this);
        collectionAdapter.setOnItemLongClickListener(this);

        fabAddCollection = view.findViewById(R.id.fab_add_collection);
        fabAddCollection.setOnClickListener(v -> showAddCollectionDialog());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        collectionViewModel.getAllCollections().observe(getViewLifecycleOwner(), collections -> collectionAdapter.setCollections(collections));
        navController = Navigation.findNavController(view);
    }

    private void showAddCollectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Corrected line: Use dialog_add_collection.xml here
        View dialogView = inflater.inflate(R.layout.dialog_edit_collection, null);

        builder.setView(dialogView)
                .setTitle(R.string.add_new_collection)
                .setPositiveButton(R.string.add, (dialog, id) -> {
                    TextInputLayout collectionNameLayout = dialogView.findViewById(R.id.collection_name_layout);
                    EditText collectionNameEditText = dialogView.findViewById(R.id.collection_name_edit_text);
                    Spinner collectionTagSpinner = dialogView.findViewById(R.id.collection_tag_spinner);
                    String name = collectionNameEditText.getText().toString();
                    String tag = collectionTagSpinner.getSelectedItem().toString();
                    Log.d(TAG, "Adding collection with name: " + name + " and tag: " + tag);
                    addNewCollection(name, tag);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

        Spinner collectionTagSpinner = dialogView.findViewById(R.id.collection_tag_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.collection_tags, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collectionTagSpinner.setAdapter(adapter);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addNewCollection(String name, String tag) {
        Collection newCollection = new Collection(name, tag);
        collectionViewModel.insert(newCollection);
    }

    @Override
    public void onItemClick(Collection collection) {
        Log.d(TAG, "onItemClick: " + collection.getName());
        Bundle bundle = new Bundle();
        bundle.putInt("collectionId", collection.getId());
        navController.navigate(R.id.action_collectionFragment_to_cardCollectionFragment, bundle);
    }

    @Override
    public void onItemLongClick(Collection collection) {
        showEditDeleteDialog(collection);
    }

    private void showEditDeleteDialog(Collection collection) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_collection, null);

        TextInputLayout collectionNameLayout = dialogView.findViewById(R.id.collection_name_layout);
        EditText collectionNameEditText = dialogView.findViewById(R.id.collection_name_edit_text);
        Spinner collectionTagSpinner = dialogView.findViewById(R.id.collection_tag_spinner);

        collectionNameEditText.setText(collection.getName());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.collection_tags, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collectionTagSpinner.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(collection.getTag());
        collectionTagSpinner.setSelection(spinnerPosition);

        builder.setView(dialogView)
                .setTitle(R.string.edit_collection)
                .setPositiveButton(R.string.save, (dialog, id) -> {
                    String newName = collectionNameEditText.getText().toString();
                    String newTag = collectionTagSpinner.getSelectedItem().toString();
                    collection.setName(newName);
                    collection.setTag(newTag);
                    collectionViewModel.update(collection);
                    Toast.makeText(getContext(), "Collection updated", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton(R.string.delete, (dialog, id) -> showDeleteConfirmationDialog(collection))
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDeleteConfirmationDialog(Collection collection) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.delete_confirmation)
                .setTitle(R.string.delete_collection)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    collectionViewModel.delete(collection);
                    Toast.makeText(getContext(), "Collection deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}