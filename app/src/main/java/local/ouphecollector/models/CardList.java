package local.ouphecollector.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.ArrayList;

public class CardList {
    @SerializedName("data")
    private List<Card> data = new ArrayList<>();

    public List<Card> getData() {
        return data;
    }
}