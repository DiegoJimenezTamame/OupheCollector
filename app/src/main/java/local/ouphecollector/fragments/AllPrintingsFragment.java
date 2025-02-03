package local.ouphecollector.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import local.ouphecollector.R;
import local.ouphecollector.models.Card;

public class AllPrintingsFragment extends Fragment {

    private static final String TAG = "AllPrintingsFragment";
    private List<Card> allPrintings;
    private RecyclerView allPrintingsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_printings, container, false);
        allPrintingsRecyclerView = view.findViewById(R.id.allPrintingsRecyclerView);
        allPrintingsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns

        if (getArguments() != null) {
            ArrayList<? extends Parcelable> parcelableList = getArguments().getParcelableArrayList("allPrintings");
            if (parcelableList != null) {
                allPrintings = new ArrayList<>();
                for (Parcelable parcelable : parcelableList) {
                    if (parcelable instanceof Card) {
                        allPrintings.add((Card) parcelable);
                    }
                }
                AllPrintingsAdapter adapter = new AllPrintingsAdapter(allPrintings);
                allPrintingsRecyclerView.setAdapter(adapter);
            } else {
                Log.e(TAG, "allPrintings is null");
            }
        } else {
            Log.e(TAG, "Arguments are null");
        }

        return view;
    }

    private static class AllPrintingsAdapter extends RecyclerView.Adapter<AllPrintingsAdapter.PrintingViewHolder> {
        private List<Card> printings;

        public AllPrintingsAdapter(List<Card> printings) {
            this.printings = printings;
        }

        @NonNull
        @Override
        public PrintingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_printing, parent, false);
            return new PrintingViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull PrintingViewHolder holder, int position) {
            Card printing = printings.get(position);
            if (printing.getImageUris() != null && printing.getImageUris().getNormal() != null) {
                Glide.with(holder.itemView.getContext())
                        .load(printing.getImageUris().getNormal())
                        .into(holder.printingImageView);
            }
            holder.itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("cardName", printing.getName());
                Navigation.findNavController(v).navigate(R.id.action_allPrintingsFragment_to_cardDetailFragment, bundle);
            });
        }

        @Override
        public int getItemCount() {
            return printings.size();
        }

        static class PrintingViewHolder extends RecyclerView.ViewHolder {
            ImageView printingImageView;

            public PrintingViewHolder(View itemView) {
                super(itemView);
                printingImageView = itemView.findViewById(R.id.printingImageView);
            }
        }
    }
}