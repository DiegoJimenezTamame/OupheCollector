package local.ouphecollector.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.models.CardCollection;

public class CardCollectionAdapter extends RecyclerView.Adapter<CardCollectionAdapter.CardCollectionViewHolder> {
    private List<CardCollection> cardCollections = new ArrayList<>();

    @NonNull
    @Override
    public CardCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_collection_item, parent, false);
        return new CardCollectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardCollectionViewHolder holder, int position) {
        CardCollection currentCardCollection = cardCollections.get(position);
        holder.textViewCardId.setText(currentCardCollection.getCard_id());
        holder.textViewQuantity.setText(String.valueOf(currentCardCollection.getQuantity()));
        holder.textViewCondition.setText(currentCardCollection.getCondition());
        holder.textViewIsFoil.setText(String.valueOf(currentCardCollection.isFoil()));
        holder.textViewLanguage.setText(currentCardCollection.getLanguage());
        holder.textViewSetCode.setText(currentCardCollection.getSet_code());
    }

    @Override
    public int getItemCount() {
        return cardCollections.size();
    }

    public void setCardCollections(List<CardCollection> cardCollections) {
        this.cardCollections = cardCollections;
        notifyDataSetChanged();
    }

    class CardCollectionViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCardId;
        private TextView textViewQuantity;
        private TextView textViewCondition;
        private TextView textViewIsFoil;
        private TextView textViewLanguage;
        private TextView textViewSetCode;

        public CardCollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCardId = itemView.findViewById(R.id.text_view_card_id);
            textViewQuantity = itemView.findViewById(R.id.text_view_quantity);
            textViewCondition = itemView.findViewById(R.id.text_view_condition);
            textViewIsFoil = itemView.findViewById(R.id.text_view_is_foil);
            textViewLanguage = itemView.findViewById(R.id.text_view_language);
            textViewSetCode = itemView.findViewById(R.id.text_view_set_code);
        }
    }
}