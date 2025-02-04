package local.ouphecollector.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.Card;
import local.ouphecollector.models.CardList;
import local.ouphecollector.models.Legalities;
import local.ouphecollector.views.ManaCostView;
import local.ouphecollector.views.SymbolManager;
import local.ouphecollector.views.SymbolizedTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardDetailFragment extends Fragment implements SymbolManager.SymbolManagerCallback {
    private TextView cardNameTextView;
    private ManaCostView cardManaCostView;
    private TextView cardTypeTextView;
    private TextView cardRarityTextView;
    private TextView cardSetTextView;
    private SymbolizedTextView cardTextTextView;
    private TextView cardPowerToughnessTextView;
    private ImageView cardImageView;
    private TextView cardFlavorTextView;
    private TextView cardColorsTextView;
    private TextView cardColorIdentityTextView;
    private TextView cardArtistTextView;
    private TextView cardLegalitiesTextView;
    private Spinner setSpinner;
    private ProgressBar progressBar;
    private TextView errorMessageTextView;
    private static final String TAG = "CardDetailFragment";
    private Card currentCard;
    private List<Card> cardPrintings;
    private static final String SHOW_ALL_PRINTINGS = "Show all Printings";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_detail, container, false);

        cardNameTextView = view.findViewById(R.id.cardNameTextView);
        cardManaCostView = view.findViewById(R.id.cardManaCostView);
        cardTypeTextView = view.findViewById(R.id.cardTypeTextView);
        cardRarityTextView = view.findViewById(R.id.cardRarityTextView);
        cardSetTextView = view.findViewById(R.id.cardSetTextView);
        cardTextTextView = view.findViewById(R.id.cardTextTextView);
        cardPowerToughnessTextView = view.findViewById(R.id.cardPowerToughnessTextView);
        cardImageView = view.findViewById(R.id.cardImageView);
        cardFlavorTextView = view.findViewById(R.id.cardFlavorTextView);
        cardColorsTextView = view.findViewById(R.id.cardColorsTextView);
        cardColorIdentityTextView = view.findViewById(R.id.cardColorIdentityTextView);
        cardArtistTextView = view.findViewById(R.id.cardArtistTextView);
        cardLegalitiesTextView = view.findViewById(R.id.cardLegalitiesTextView);
        setSpinner = view.findViewById(R.id.setSpinner);
        progressBar = view.findViewById(R.id.progressBar);
        errorMessageTextView = view.findViewById(R.id.errorMessageTextView);

        Log.d(TAG, "onCreateView: Started");

        if (getArguments() != null) {
            if (getArguments().containsKey("card")) {
                // Card object was passed (from AllPrintingsFragment)
                Card newCard = getArguments().getParcelable("card");
                Log.d(TAG, "onCreateView: Card received from arguments: " + (newCard != null ? newCard.getName() : "null"));
                if (newCard != null) {
                    currentCard = newCard;
                    Log.d(TAG, "onCreateView: currentCard updated to: " + currentCard.getName());
                    updateUI(currentCard);
                } else {
                    Log.e(TAG, "onCreateView: newCard is null");
                }
            } else if (getArguments().containsKey("cardName")) {
                // Card name was passed (from main search)
                String cardName = getArguments().getString("cardName");
                Log.d(TAG, "onCreateView: cardName received: " + cardName);
                showLoading();
                fetchCardDetails(cardName);
            }
        } else {
            Log.e(TAG, "onCreateView: getArguments() is null");
        }

        Log.d(TAG, "onCreateView: Finished");
        return view;
    }

    private void fetchCardDetails(String cardName) {
        Log.d(TAG, "fetchCardDetails: Started for cardName: " + cardName);
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<Card> call = apiService.getCardByName(cardName);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Card> call, @NonNull Response<Card> response) {
                Log.d(TAG, "fetchCardDetails: onResponse");
                if (response.isSuccessful() && response.body() != null) {
                    currentCard = response.body();
                    Log.d(TAG, "fetchCardDetails: currentCard updated to: " + currentCard.getName());
                    if (SymbolManager.getInstance(getContext()).isInitialized()) {
                        updateUI(currentCard);
                        updateCardPrintings(currentCard);
                    } else {
                        Log.d(TAG, "fetchCardDetails: Symbols not loaded yet, waiting for callback");
                        SymbolManager.getInstance(getContext()).addCallback(CardDetailFragment.this);
                    }
                } else {
                    showErrorMessage(getString(R.string.error_loading_data));
                    Log.e(TAG, "fetchCardDetails: Error fetching card details: " + response.message() + " " + response.code());
                }
                hideLoading();
            }

            @Override
            public void onFailure(@NonNull Call<Card> call, @NonNull Throwable t) {
                showErrorMessage(getString(R.string.error_loading_data));
                Log.e(TAG, "fetchCardDetails: Error fetching card details", t);
                hideLoading();
            }
        });
    }

    private void updateCardPrintings(Card card) {
        if (card == null) {
            Log.e(TAG, "updateCardPrintings: Card is null");
            return;
        }
        if (card.getPrintsSearchUri() == null || card.getPrintsSearchUri().isEmpty()) {
            Log.e(TAG, "updateCardPrintings: Prints search URI is null or empty");
            return;
        }
        Log.d(TAG, "updateCardPrintings: Started for card: " + card.getName());
        Log.d(TAG, "updateCardPrintings: currentCard.getName() = " + card.getName());
        Log.d(TAG, "updateCardPrintings: currentCard.getPrintsSearchUri() = " + card.getPrintsSearchUri());
        showLoading();
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<CardList> call = apiService.getCardPrintings(card.getPrintsSearchUri());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CardList> call, @NonNull Response<CardList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cardPrintings = response.body().getData();
                    Log.d(TAG, "updateCardPrintings: cardPrintings.size() = " + cardPrintings.size());
                    if (cardPrintings.isEmpty()) {
                        showErrorMessage(getString(R.string.no_results_found));
                    } else {
                        populateSetSpinner(cardPrintings);
                    }
                } else {
                    showErrorMessage(getString(R.string.error_loading_data));
                    Log.e(TAG, "Error fetching card printings: " + response.message() + " " + response.code());
                }
                hideLoading();
            }

            @Override
            public void onFailure(@NonNull Call<CardList> call, @NonNull Throwable t) {
                showErrorMessage(getString(R.string.error_loading_data));
                Log.e(TAG, "Error fetching card printings", t);
                hideLoading();
            }
        });
    }

    private void populateSetSpinner(List<Card> printings) {
        if (printings == null || printings.isEmpty()) {
            Log.e(TAG, "populateSetSpinner: No card printings found");
            return;
        }
        Log.d(TAG, "populateSetSpinner: Started with " + printings.size() + " printings");
        List<String> setNames = new ArrayList<>();
        List<Card> limitedPrintings = new ArrayList<>();
        int maxPrintings = Math.min(3, printings.size());
        for (int i = 0; i < maxPrintings; i++) {
            Card printing = printings.get(i);
            setNames.add(printing.getExpansionName());
            limitedPrintings.add(printing);
        }
        setNames.add(SHOW_ALL_PRINTINGS);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, setNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setSpinner.setAdapter(adapter);

        setSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSetName = (String) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: Selected set: " + selectedSetName);
                if (selectedSetName.equals(SHOW_ALL_PRINTINGS)) {
                    Log.d(TAG, "onItemSelected: Navigating to AllPrintingsFragment");
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("allPrintings", (ArrayList<? extends Parcelable>) cardPrintings);
                    Navigation.findNavController(view).navigate(R.id.action_cardDetailFragment_to_allPrintingsFragment, bundle);
                } else {
                    for (Card printing : cardPrintings) {
                        if (printing.getExpansionName().equals(selectedSetName)) {
                            Log.d(TAG, "onItemSelected: Found matching printing: " + printing.getName());
                            currentCard = printing;
                            Log.d(TAG, "onItemSelected: currentCard updated to: " + currentCard.getName());
                            updateUI(currentCard);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        Log.d(TAG, "populateSetSpinner: Finished");
    }

    private void updateUI(Card card) {
        if (card == null) {
            Log.e(TAG, "updateUI: Card object is null");
            return;
        }
        Log.d(TAG, "updateUI: Started for card: " + card.getName());
        if (card.getName() != null) {
            cardNameTextView.setText(card.getName());
            Log.d(TAG, "updateUI: Card name set: " + card.getName());
        } else {
            cardNameTextView.setText("");
        }
        if (card.getManaCost() != null) {
            cardManaCostView.setManaCost(card.getManaCost());
            Log.d(TAG, "updateUI: Card mana cost set: " + card.getManaCost());
        } else {
            cardManaCostView.setManaCost("");
        }
        if (card.getTypeLine() != null) {
            cardTypeTextView.setText(getString(R.string.type_format, card.getTypeLine()));
        } else {
            cardTypeTextView.setText("");
        }
        if (card.getRarity() != null) {
            cardRarityTextView.setText(getString(R.string.rarity_format, card.getRarity()));
        } else {
            cardRarityTextView.setText("");
        }
        if (card.getSet() != null) {
            cardSetTextView.setText(getString(R.string.set_format, card.getSet()));
        } else {
            cardSetTextView.setText("");
        }
        if (card.getOracleText() != null) {
            cardTextTextView.setSymbolizedText(card.getOracleText());
        } else {
            cardTextTextView.setSymbolizedText("");
        }

        // Conditional display for Power/Toughness
        if (card.getTypeLine() != null && card.getTypeLine().contains("Creature")) {
            if (card.getPower() != null && card.getToughness() != null) {
                cardPowerToughnessTextView.setText(getString(R.string.power_toughness_format, card.getPower(), card.getToughness()));
                cardPowerToughnessTextView.setVisibility(View.VISIBLE);
            } else {
                cardPowerToughnessTextView.setVisibility(View.GONE);
            }
        } else {
            cardPowerToughnessTextView.setVisibility(View.GONE);
        }

        // Conditional display for Flavor Text
        if (card.getFlavorText() != null && !card.getFlavorText().isEmpty()) {
            cardFlavorTextView.setText(getString(R.string.flavor_text_format, card.getFlavorText()));
            cardFlavorTextView.setVisibility(View.VISIBLE);
        } else {
            cardFlavorTextView.setVisibility(View.GONE);
        }

        // Display Colors
        if (card.getColors() != null && !card.getColors().isEmpty()) {
            cardColorsTextView.setText(getString(R.string.colors_format, String.join(", ", card.getColors())));
            cardColorsTextView.setVisibility(View.VISIBLE);
        } else {
            cardColorsTextView.setVisibility(View.GONE);
        }

        // Display Color Identity
        if (card.getColorIdentity() != null && !card.getColorIdentity().isEmpty()) {
            cardColorIdentityTextView.setText(getString(R.string.color_identity_format, String.join(", ", card.getColorIdentity())));
            cardColorIdentityTextView.setVisibility(View.VISIBLE);
        } else {
            cardColorIdentityTextView.setVisibility(View.GONE);
        }

        // Display Artist
        if (card.getArtist() != null && !card.getArtist().isEmpty()) {
            cardArtistTextView.setText(getString(R.string.artist_format, card.getArtist()));
            cardArtistTextView.setVisibility(View.VISIBLE);
        } else {
            cardArtistTextView.setVisibility(View.GONE);
        }

        // Display Legalities
        Legalities legalities = card.getLegalities();
        if (legalities != null) {
            String legalitiesText = "Standard: " + legalities.getStandard() + "\n" +
                    "Future: " + legalities.getFuture() + "\n" +
                    "Historic: " + legalities.getHistoric() + "\n" +
                    "Gladiator: " + legalities.getGladiator() + "\n" +
                    "Pioneer: " + legalities.getPioneer() + "\n" +
                    "Explorer: " + legalities.getExplorer() + "\n" +
                    "Modern: " + legalities.getModern() + "\n" +
                    "Legacy: " + legalities.getLegacy() + "\n" +
                    "Pauper: " + legalities.getPauper() + "\n" +
                    "Vintage: " + legalities.getVintage() + "\n" +
                    "Penny: " + legalities.getPenny() + "\n" +
                    "Commander: " + legalities.getCommander() + "\n" +
                    "Oathbreaker: " + legalities.getOathbreaker() + "\n" +
                    "Brawl: " + legalities.getBrawl() + "\n" +
                    "HistoricBrawl: " + legalities.getHistoricBrawl() + "\n" +
                    "Alchemy: " + legalities.getAlchemy() + "\n" +
                    "PauperCommander: " + legalities.getPauperCommander();
            cardLegalitiesTextView.setText(legalitiesText);
            cardLegalitiesTextView.setVisibility(View.VISIBLE);
        } else {
            cardLegalitiesTextView.setVisibility(View.GONE);
        }
        if (card.getImageUris() != null && card.getImageUris().getNormal() != null) {
            Glide.with(getContext())
                    .load(card.getImageUris().getNormal())
                    .into(cardImageView);
        } else {
            Glide.with(getContext())
                    .load(R.drawable.no_image)
                    .into(cardImageView);
        }
        Log.d(TAG, "updateUI: Finished for card: " + card.getName());
    }


    @Override
    public void onSymbolsLoaded() {
        Log.d(TAG, "onSymbolsLoaded: Symbols loaded, updating UI");
        if (currentCard != null) {
            updateUI(currentCard);
        }
    }

    private void showLoading() {
        Log.d(TAG, "showLoading: Showing loading indicator");
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (errorMessageTextView != null) {
            errorMessageTextView.setVisibility(View.GONE);
        }
    }

    private void hideLoading() {
        Log.d(TAG, "hideLoading: Hiding loading indicator");
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showErrorMessage(String message) {
        Log.e(TAG, "showErrorMessage: Showing error message: " + message);
        if (errorMessageTextView != null) {
            errorMessageTextView.setText(message);
            errorMessageTextView.setVisibility(View.VISIBLE);
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }


}

