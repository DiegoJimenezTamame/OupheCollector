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

public class CardPrintingAdapter extends RecyclerView.Adapter<CardPrintingAdapter.CardPrintingViewHolder> {

    private List<Card> cardPrintings;
    private final OnCardClickListener listener;

    public interface OnCardClickListener {
        void onCardClick(Card card);
    }

    public CardPrintingAdapter(List<Card> cardPrintings, OnCardClickListener listener) {
        this.cardPrintings = cardPrintings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardPrintingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_printing_item, parent, false);
        return new CardPrintingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardPrintingViewHolder holder, int position) {
        Card card = cardPrintings.get(position);
        holder.bind(card, listener);
    }

    @Override
    public int getItemCount() {
        return cardPrintings.size();
    }

    public void updateData(List<Card> newCardPrintings) {
        cardPrintings.clear();
        cardPrintings.addAll(newCardPrintings);
        notifyDataSetChanged();
    }

    static class CardPrintingViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cardImageView;
        private final TextView cardNameTextView;
        private final TextView cardSetTextView;

        public CardPrintingViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.cardImageView);
            cardNameTextView = itemView.findViewById(R.id.cardNameTextView);
            cardSetTextView = itemView.findViewById(R.id.cardSetTextView);
        }

        public void bind(final Card card, final OnCardClickListener listener) {
            cardNameTextView.setText(card.getName());
            cardSetTextView.setText(card.getExpansionName()); // Use getSetName() instead of getExpansionName()
            if (card.getImageUris() != null && card.getImageUris().getSmall() != null) {
                Glide.with(itemView.getContext())
                        .load(card.getImageUris().getSmall())
                        .into(cardImageView);
            } else {
                Glide.with(itemView.getContext())
                        .load(R.drawable.no_image)
                        .into(cardImageView);
            }
            itemView.setOnClickListener(v -> listener.onCardClick(card));
        }
    }
}