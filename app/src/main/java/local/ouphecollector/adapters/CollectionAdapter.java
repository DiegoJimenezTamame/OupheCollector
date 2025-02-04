package local.ouphecollector.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import local.ouphecollector.R;
import local.ouphecollector.models.Collection;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {
    private static final String TAG = "CollectionAdapter";
    private List<Collection> collections = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;

    public interface OnItemClickListener {
        void onItemClick(Collection collection);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Collection collection);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item, parent, false);
        return new CollectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        Collection currentCollection = collections.get(position);
        holder.textViewName.setText(currentCollection.getName());
        holder.textViewTag.setText(currentCollection.getTag());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        holder.textViewCreatedAt.setText("Created: " + dateFormat.format(currentCollection.getCreatedAt()));
        holder.textViewLastModified.setText("Last Modified: " + dateFormat.format(currentCollection.getLastModified()));
        Log.d(TAG, "onBindViewHolder: " + currentCollection.getName() + " " + currentCollection.getTag());
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
        notifyDataSetChanged();
    }

    class CollectionViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewTag;
        private TextView textViewCreatedAt;
        private TextView textViewLastModified;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewTag = itemView.findViewById(R.id.text_view_tag);
            textViewCreatedAt = itemView.findViewById(R.id.text_view_created_at);
            textViewLastModified = itemView.findViewById(R.id.text_view_last_modified);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(collections.get(position));
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                    longClickListener.onItemLongClick(collections.get(position));
                    return true;
                }
                return false;
            });
        }
    }
}