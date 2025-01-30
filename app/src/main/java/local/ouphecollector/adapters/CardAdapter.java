package local.ouphecollector.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.models.Card;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Card> cards;
    private final CardClickListener cardClickListener;

    public interface CardClickListener {
        void onCardClicked(Card card);
    }

    public CardAdapter(List<Card> cards, CardClickListener cardClickListener) {
        this.cards = cards;
        this.cardClickListener = cardClickListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view, cardClickListener, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.cardNameTextView.setText(card.getName());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView cardNameTextView;

        public CardViewHolder(@NonNull View itemView, CardClickListener cardClickListener, CardAdapter cardAdapter) {
            super(itemView);
            cardNameTextView = itemView.findViewById(R.id.cardNameTextView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Card card = cardAdapter.cards.get(position);
                    cardClickListener.onCardClicked(card);
                }
            });
        }
    }
}