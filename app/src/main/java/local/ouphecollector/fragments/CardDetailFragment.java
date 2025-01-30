package local.ouphecollector.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import local.ouphecollector.R;

public class CardDetailFragment extends Fragment {
    private static final String ARG_CARD_NAME = "cardName";

    private String cardName;

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

        TextView cardNameTextView = view.findViewById(R.id.cardNameTextView);
        cardNameTextView.setText(cardName);

        return view;
    }
}