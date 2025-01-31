package local.ouphecollector.models;

import java.util.List;

public class CardSymbol {
    private String object;
    private String symbol;
    private String svg_uri;
    private String loose_variant;
    private String english;
    private boolean transposable;
    private boolean represents_mana;
    private boolean appears_in_mana_costs;
    private Double mana_value;
    private boolean hybrid;
    private boolean phyrexian;
    private Double cmc;
    private boolean funny;
    private List<String> colors;
    private List<String> gatherer_alternates;

    // Getters and setters for all fields
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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

    public String getLoose_variant() {
        return loose_variant;
    }

    public void setLoose_variant(String loose_variant) {
        this.loose_variant = loose_variant;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public boolean isTransposable() {
        return transposable;
    }

    public void setTransposable(boolean transposable) {
        this.transposable = transposable;
    }

    public boolean isRepresents_mana() {
        return represents_mana;
    }

    public void setRepresents_mana(boolean represents_mana) {
        this.represents_mana = represents_mana;
    }

    public boolean isAppears_in_mana_costs() {
        return appears_in_mana_costs;
    }

    public void setAppears_in_mana_costs(boolean appears_in_mana_costs) {
        this.appears_in_mana_costs = appears_in_mana_costs;
    }

    public Double getMana_value() {
        return mana_value;
    }

    public void setMana_value(Double mana_value) {
        this.mana_value = mana_value;
    }

    public boolean isHybrid() {
        return hybrid;
    }

    public void setHybrid(boolean hybrid) {
        this.hybrid = hybrid;
    }

    public boolean isPhyrexian() {
        return phyrexian;
    }

    public void setPhyrexian(boolean phyrexian) {
        this.phyrexian = phyrexian;
    }

    public Double getCmc() {
        return cmc;
    }

    public void setCmc(Double cmc) {
        this.cmc = cmc;
    }

    public boolean isFunny() {
        return funny;
    }

    public void setFunny(boolean funny) {
        this.funny = funny;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getGatherer_alternates() {
        return gatherer_alternates;
    }

    public void setGatherer_alternates(List<String> gatherer_alternates) {
        this.gatherer_alternates = gatherer_alternates;
    }
}