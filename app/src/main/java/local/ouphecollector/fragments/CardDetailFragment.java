package local.ouphecollector.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.Card;
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
    private static final String TAG = "CardDetailFragment";
    private Card cardToUpdate;

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

        assert getArguments() != null;
        String cardName = getArguments().getString("cardName");
        fetchCardDetails(cardName);

        return view;
    }

    private void fetchCardDetails(String cardName) {
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<Card> call = apiService.getCardByName(cardName);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Card> call, @NonNull Response<Card> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cardToUpdate = response.body();
                    if (SymbolManager.getInstance().isInitialized()) {
                        updateUI(cardToUpdate);
                    } else {
                        Log.d(TAG, "Symbols not loaded yet, waiting for callback");
                        SymbolManager.getInstance().addCallback(CardDetailFragment.this);
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
        cardManaCostView.setManaCost(card.getManaCost());
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
        if (cardToUpdate != null) {
            updateUI(cardToUpdate);
        }
    }
}

