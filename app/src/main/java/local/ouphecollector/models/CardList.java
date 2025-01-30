package local.ouphecollector.models;

import java.util.List;

public class CardList {
    private List<Card> data;

    public CardList(List<Card> data) {
        this.data = data;
    }

    public List<Card> getData() {
        return data;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }
}