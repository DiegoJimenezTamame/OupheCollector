package local.ouphecollector.api;

import local.ouphecollector.models.Card;
import com.google.gson.annotations.SerializedName;

public class CardResponse {
    @SerializedName("data")
    private Card card; // Changed from 'data' to 'card'

    public Card getCard() { // Changed from 'getData' to 'getCard'
        return card;
    }
}