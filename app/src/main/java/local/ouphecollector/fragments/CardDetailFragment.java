package local.ouphecollector.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import local.ouphecollector.R;
import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.Card;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardDetailFragment extends Fragment {
    private static final String ARG_CARD_NAME = "cardName";
    private String cardName;
    private TextView cardNameTextView;
    private TextView cardManaCostTextView;
    private TextView cardTypeTextView;
    private TextView cardRarityTextView;
    private TextView cardSetTextView;
    private TextView cardTextTextView;
    private TextView cardPowerToughnessTextView;

    public static CardDetailFragment newInstance(String cardName) {
        CardDetailFragment fragment = new CardDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CARD_NAME, cardName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardName = getArguments().getString(ARG_CARD_NAME);
        }
    }

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

        fetchCardDetails(cardName);

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

    private void updateUI(Card card) {
        cardNameTextView.setText(card.getName());
        cardManaCostTextView.setText(getString(R.string.mana_cost_format, card.getManaCost()));
        cardTypeTextView.setText(getString(R.string.type_format, card.getTypeLine()));
        cardRarityTextView.setText(getString(R.string.rarity_format, card.getRarity()));
        cardSetTextView.setText(getString(R.string.set_format, card.getSet()));
        cardTextTextView.setText(getString(R.string.card_text_format, card.getOracleText()));
        cardPowerToughnessTextView.setText(getString(R.string.power_toughness_format, card.getPower(), card.getToughness()));
    }
}