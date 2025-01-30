package local.ouphecollector.fragments;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.Card;
import local.ouphecollector.viewmodels.CardViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import local.ouphecollector.models.RelatedCard;
import local.ouphecollector.models.Legalities;



public class CardDetailFragment extends Fragment {
    private TextView cardNameTextView;
    private TextView cardManaCostTextView;
    private TextView cardTypeTextView;
    private TextView cardRarityTextView;
    private TextView cardSetTextView;
    private TextView cardTextTextView;
    private TextView cardPowerToughnessTextView;
    private ImageView cardImageView;
    private TextView cardFlavorTextView;
    private TextView cardColorsTextView;
    private TextView cardColorIdentityTextView;
    private TextView cardArtistTextView;
    private TextView cardOracleTextView;
    private TextView cardLegalitiesTextView;
    private CardViewModel cardViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_detail, container, false);

        cardNameTextView = view.findViewById(R.id.cardNameTextView);
        cardManaCostTextView = view.findViewById(R.id.cardManaCostTextView);
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
        cardOracleTextView = view.findViewById(R.id.cardOracleTextView);
        cardLegalitiesTextView = view.findViewById(R.id.cardLegalitiesTextView);

        // Get the arguments from the Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String cardName = bundle.getString("cardName");
            if (cardName != null && !cardName.isEmpty()) {
                cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
                fetchCardDetails(cardName);
            } else {
                Log.e("CardDetailFragment", "Card name is empty or null");
            }
        }

        return view;
    }

    private void fetchCardDetails(String cardName) {
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<Card> call = apiService.getCardByName(cardName);
        call.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Card card = response.body();
                    updateUI(card);
                } else {
                    Log.e("CardDetailFragment", "Error fetching card details: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                Log.e("CardDetailFragment", "Error fetching card details", t);
            }
        });
    }
    private void showDifferentVersionsDialog(List<RelatedCard> relatedCards) {
        if (relatedCards == null || relatedCards.isEmpty()) {
            // No related cards, so don't show the dialog
            return;
        }
        String[] cardNames = new String[relatedCards.size()];
        for (int i = 0; i < relatedCards.size(); i++) {
            cardNames[i] = relatedCards.get(i).getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Different Versions")
                .setItems(cardNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        String selectedCardName = cardNames[which];
                        fetchCardDetails(selectedCardName);
                    }
                });
        builder.create().show();
    }

    private void updateUI(Card card) {
        List<RelatedCard> relatedCards = card.getAllParts();
        showDifferentVersionsDialog(relatedCards);
        cardNameTextView.setText(card.getName());
        cardManaCostTextView.setText(getString(R.string.mana_cost_format, card.getManaCost()));
        cardTypeTextView.setText(getString(R.string.type_format, card.getTypeLine()));
        cardRarityTextView.setText(getString(R.string.rarity_format, card.getRarity()));
        cardSetTextView.setText(getString(R.string.set_format, card.getSet()));
        cardTextTextView.setText(getString(R.string.card_text_format, card.getOracleText()));

        // Conditional display for Power/Toughness
        if (card.getTypeLine().contains("Creature")) {
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

        // Display Oracle Text
        if (card.getOracleText() != null && !card.getOracleText().isEmpty()) {
            cardOracleTextView.setText(getString(R.string.oracle_text_format, card.getOracleText()));
            cardOracleTextView.setVisibility(View.VISIBLE);
        } else {
            cardOracleTextView.setVisibility(View.GONE);
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
}

