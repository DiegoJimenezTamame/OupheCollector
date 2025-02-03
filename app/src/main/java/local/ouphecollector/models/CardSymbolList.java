package local.ouphecollector.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardSymbolList {
    @SerializedName("data")
    private List<CardSymbol> data;

    public CardSymbolList() {
    }

    public List<CardSymbol> getData() {
        return data;
    }

    public void setData(List<CardSymbol> data) {
        this.data = data;
    }
}