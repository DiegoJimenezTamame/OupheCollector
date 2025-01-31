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
import local.ouphecollector.models.Card;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Card> cards;
    private CardClickListener cardClickListener;

    public CardAdapter(List<Card> cards, CardClickListener cardClickListener) {
        this.cards = cards;
        this.cardClickListener = cardClickListener;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
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

        // Load the image using Glide
        if (card.getImageUris() != null && card.getImageUris().getSmall() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(card.getImageUris().getSmall())
                    .into(holder.cardImageView);
        } else {
            // Load a placeholder image if the card doesn't have an image
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.no_image)
                    .into(holder.cardImageView);
        }

        holder.itemView.setOnClickListener(v -> {
            cardClickListener.onCardClicked(card);
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView cardNameTextView;
        public ImageView cardImageView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNameTextView = itemView.findViewById(R.id.cardNameTextView);
            cardImageView = itemView.findViewById(R.id.cardImageView);
        }
    }

    public interface CardClickListener {
        void onCardClicked(Card card);
    }
}