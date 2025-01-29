package local.ouphecollector.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScryfallResponse {
    private String object;
    @SerializedName("total_cards")
    private int totalCards;
    @SerializedName("has_more")
    private boolean hasMore;
    private String next_page;
    private List<ScryfallCard> data;

    // Constructor, getters, and setters
    public ScryfallResponse(String object, int totalCards, boolean hasMore, String next_page, List<ScryfallCard> data) {
        this.object = object;
        this.totalCards = totalCards;
        this.hasMore = hasMore;
        this.next_page = next_page;
        this.data = data;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(int totalCards) {
        this.totalCards = totalCards;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getNext_page() {
        return next_page;
    }

    public void setNext_page(String next_page) {
        this.next_page = next_page;
    }

    public List<ScryfallCard> getData() {
        return data;
    }

    public void setData(List<ScryfallCard> data) {
        this.data = data;
    }
}