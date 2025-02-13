package local.ouphecollector.models;

import com.google.gson.annotations.SerializedName;

public class CardSymbol {
    private String symbol;
    @SerializedName("svg_uri")
    private String svg_uri;

    public CardSymbol() {
    }

    public CardSymbol(String symbol, String svg_uri) {
        this.symbol = symbol;
        this.svg_uri = svg_uri;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSvg_uri() {
        return svg_uri;
    }

    public void setSvg_uri(String svg_uri) {
        this.svg_uri = svg_uri;
    }
}