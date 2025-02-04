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

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder> {

    private List<String> recentSearches;
    private OnRecentSearchClickListener listener;

    public interface OnRecentSearchClickListener {
        void onRecentSearchClicked(String cardName);
    }

    public RecentSearchAdapter(List<String> recentSearches, OnRecentSearchClickListener listener) {
        this.recentSearches = recentSearches;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecentSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_search, parent, false);
        return new RecentSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentSearchViewHolder holder, int position) {
        String cardName = recentSearches.get(position);
        holder.cardNameTextView.setText(cardName);
        fetchCardImage(cardName, holder.cardImageView);
        holder.itemView.setOnClickListener(v -> listener.onRecentSearchClicked(cardName));
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
        return recentSearches.size();
    }

    public static class RecentSearchViewHolder extends RecyclerView.ViewHolder {
        TextView cardNameTextView;
        ImageView cardImageView;

        public RecentSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNameTextView = itemView.findViewById(R.id.recentSearchCardName);
            cardImageView = itemView.findViewById(R.id.recentSearchCardImage);
        }
    }
}