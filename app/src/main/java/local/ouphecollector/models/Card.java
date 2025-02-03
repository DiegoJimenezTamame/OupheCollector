package local.ouphecollector.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Card {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public String id ="";

    @SerializedName("name")
    private String name;

    @SerializedName("mana_cost")
    private String manaCost;

    @SerializedName("type_line")
    private String typeLine;

    @SerializedName("oracle_text")
    private String oracleText;

    @SerializedName("power")
    private String power;

    @SerializedName("toughness")
    private String toughness;

    @SerializedName("colors")
    private List<String> colors;

    @SerializedName("color_identity")
    private List<String> colorIdentity;

    @SerializedName("artist")
    private String artist;

    @SerializedName("rarity")
    private String rarity;

    @SerializedName("set")
    private String set;

    @SerializedName("flavor_text")
    private String flavorText;

    @SerializedName("image_uris")
    private ImageUris imageUris;

    @SerializedName("legalities")
    private Legalities legalities;

    @SerializedName("related_uris")
    private RelatedUris relatedUris;

    @SerializedName("all_parts")
    private List<RelatedCard> allParts;

    @SerializedName("prints_search_uri")
    private String printsSearchUri;

    // --- Getters and Setters ---

    public String getPrintsSearchUri() {
        return printsSearchUri;
    }

    public void setPrintsSearchUri(String printsSearchUri) {
        this.printsSearchUri = printsSearchUri;
    }

    public List<RelatedCard> getAllParts() {
        return allParts;
    }

    public void setAllParts(List<RelatedCard> allParts) {
        this.allParts = allParts;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getTypeLine() {
        return typeLine;
    }

    public void setTypeLine(String typeLine) {
        this.typeLine = typeLine;
    }

    public String getOracleText() {
        return oracleText;
    }

    public void setOracleText(String oracleText) {
        this.oracleText = oracleText;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getColorIdentity() {
        return colorIdentity;
    }

    public void setColorIdentity(List<String> colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public ImageUris getImageUris() {
        return imageUris;
    }

    public void setImageUris(ImageUris imageUris) {
        this.imageUris = imageUris;
    }

    public Legalities getLegalities() {
        return legalities;
    }

    public void setLegalities(Legalities legalities) {
        this.legalities = legalities;
    }

    public RelatedUris getRelatedUris() {
        return relatedUris;
    }

    public void setRelatedUris(RelatedUris relatedUris) {
        this.relatedUris = relatedUris;
    }

}