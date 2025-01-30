package local.ouphecollector.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardList {
    @SerializedName("data")
    private List<Card> cards;

    public CardList(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}