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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import local.ouphecollector.models.RelatedCard;
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
    private static final String TAG = "CardDetailFragment";
    private Card currentCard;
    private static final String ARG_CARD_NAME = "cardName";
    private List<Card> cardPrintings;
    private static final String SHOW_ALL_PRINTINGS = "Show all Printings";
    private List<Card> limitedPrintings;

    public static CardDetailFragment newInstance(String cardName) {
        CardDetailFragment fragment = new CardDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CARD_NAME, cardName);
        fragment.setArguments(args);
        return fragment;
    }

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

        if (getArguments() != null) {
            String cardName = getArguments().getString(ARG_CARD_NAME);
            fetchCardDetails(cardName);
        }

        return view;
    }

    private void fetchCardDetails(String cardName) {
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<Card> call = apiService.getCardByName(cardName);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Card> call, @NonNull Response<Card> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentCard = response.body();
                    if (SymbolManager.getInstance(getContext()).isInitialized()) {
                        updateUI(currentCard);
                        fetchCardPrintings(currentCard);
                    } else {
                        Log.d(TAG, "Symbols not loaded yet, waiting for callback");
                        SymbolManager.getInstance(getContext()).addCallback(CardDetailFragment.this);
                    }
                } else {
                    Log.e(TAG, "Error fetching card details: " + response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Card> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching card details", t);
            }
        });
    }

    private void fetchCardPrintings(Card card) {
        if (card.getPrintsSearchUri() == null || card.getPrintsSearchUri().isEmpty()) {
            Log.e(TAG, "Prints search URI is null or empty");
            return;
        }
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<CardList> call = apiService.getCardPrintings(card.getPrintsSearchUri());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CardList> call, @NonNull Response<CardList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cardPrintings = response.body().getData();
                    populateSetSpinner(cardPrintings);
                } else {
                    Log.e(TAG, "Error fetching card printings: " + response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CardList> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching card printings", t);
            }
        });
    }

    private void populateSetSpinner(List<Card> printings) {
        if (printings == null || printings.isEmpty()) {
            Log.e(TAG, "No card printings found");
            return;
        }
        List<String> setNames = new ArrayList<>();
        limitedPrintings = new ArrayList<>();
        int maxPrintings = Math.min(3, printings.size());
        for (int i = 0; i < maxPrintings; i++) {
            Card printing = printings.get(i);
            setNames.add(printing.getSet());
            limitedPrintings.add(printing);
        }
        if (printings.size() > 3) {
            setNames.add(SHOW_ALL_PRINTINGS);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, setNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setSpinner.setAdapter(adapter);
        setSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (setNames.get(position).equals(SHOW_ALL_PRINTINGS)) {
                    // Navigate to the new fragment to show all printings
                    Bundle bundle = new Bundle();
                    ArrayList<? extends Parcelable> parcelablePrintings = new ArrayList<>(printings);
                    bundle.putParcelableArrayList("allPrintings", parcelablePrintings);
                    Navigation.findNavController(view).navigate(R.id.action_cardDetailFragment_to_allPrintingsFragment, bundle);
                } else {
                    Card selectedPrinting = limitedPrintings.get(position);
                    updateUI(selectedPrinting);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void showDifferentVersionsDialog(List<RelatedCard> relatedCards) {
        if (relatedCards == null || relatedCards.isEmpty()) {
            return;
        }
        String[] cardNames = relatedCards.stream()
                .map(RelatedCard::getName)
                .toArray(String[]::new);

        new AlertDialog.Builder(requireContext())
                .setTitle("Different Versions")
                .setItems(cardNames, (dialog, which) -> fetchCardDetails(cardNames[which]))
                .show();
    }
    private void updateUI(Card card) {

        if (card == null) {
            Log.e(TAG, "Card object is null");
            return;
        }
        if (card.getAllParts() != null) {
            showDifferentVersionsDialog(card.getAllParts());
        }
        cardNameTextView.setText(card.getName());
        Log.d(TAG, "Card name set: " + card.getName());
        cardManaCostView.setManaCost(card.getManaCost());
        Log.d(TAG, "Card mana cost set: " + card.getManaCost());
        cardTypeTextView.setText(getString(R.string.type_format, card.getTypeLine()));
        cardRarityTextView.setText(getString(R.string.rarity_format, card.getRarity()));
        cardSetTextView.setText(getString(R.string.set_format, card.getSet()));
        cardTextTextView.setSymbolizedText(card.getOracleText());

        // Conditional display for Power/Toughness
        if (card.getTypeLine() != null && card.getTypeLine().contains("Creature")) {
            cardPowerToughnessTextView.setText(getString(R.string.power_toughness_format, card.getPower(), card.getToughness()));
            cardPowerToughnessTextView.setVisibility(View.VISIBLE);
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
                    "Brawl: " + legalities.getBrawl() + "\n" +
                    "HistoricBrawl: " + legalities.getHistoricBrawl() + "\n" +
                    "Alchemy: " + legalities.getAlchemy() + "\n" +
                    "PauperCommander: " + legalities.getPauperCommander() + "\n" +
                    "Duel: " + legalities.getDuel() + "\n" +
                    "Oldschool: " + legalities.getOldschool() + "\n" +
                    "Premodern: " + legalities.getPremodern();
            cardLegalitiesTextView.setText(legalitiesText);
            cardLegalitiesTextView.setVisibility(View.VISIBLE);
        } else {
            cardLegalitiesTextView.setVisibility(View.GONE);
        }

        // Load Image
        if (card.getImageUris() != null && card.getImageUris().getNormal() != null) {
            Glide.with(this)
                    .load(card.getImageUris().getNormal())
                    .into(cardImageView);
        }
    }

    @Override
    public void onSymbolsLoaded() {
        Log.d(TAG, "onSymbolsLoaded called");
        if (currentCard != null) {
            updateUI(currentCard);
        }
    }
}

