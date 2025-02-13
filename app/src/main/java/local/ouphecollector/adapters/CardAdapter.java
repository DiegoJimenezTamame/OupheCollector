package local.ouphecollector.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.Card;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Card> cards;
    private OnCardClickListener listener;

    public interface OnCardClickListener {
        void onCardClicked(Card card);
    }

    public CardAdapter(List<Card> cards, OnCardClickListener listener) {
        this.cards = cards;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.cardNameTextView.setText(card.getName());
        fetchCardImage(card.getName(), holder.cardImageView);
        holder.itemView.setOnClickListener(v -> listener.onCardClicked(card));
    }

    private void fetchCardImage(String cardName, ImageView cardImageView) {
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<Card> call = apiService.getCardByName(cardName);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Card> call, @NonNull Response<Card> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Card card = response.body();
                    if (card.getImageUris() != null && card.getImageUris().getSmall() != null) {
                        Glide.with(cardImageView.getContext())
                                .load(card.getImageUris().getSmall())
                                .into(cardImageView);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Card> call, @NonNull Throwable t) {
                // Handle failure
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView cardNameTextView;
        ImageView cardImageView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNameTextView = itemView.findViewById(R.id.cardName);
            cardImageView = itemView.findViewById(R.id.cardImage);
        }
    }
}