package local.ouphecollector.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "cards")
public class Card {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    @Embedded
    @SerializedName("image_uris")
    private ImageUris imageUris;
    @SerializedName("mana_cost")
    private String manaCost;
    @SerializedName("type_line")
    private String typeLine;
    @SerializedName("oracle_text")
    private String oracleText;
    @SerializedName("flavor_text")
    private String flavorText;
    private String set;
    private String rarity;
    @SerializedName("collector_number")
    private String collectorNumber;
    private String power;
    private String toughness;
    @Embedded
    private Legalities legalities;

    public Card(String id, String name, ImageUris imageUris, String manaCost, String typeLine, String oracleText, String flavorText, String set, String rarity, String collectorNumber, String power, String toughness, Legalities legalities) {
        this.id = id;
        this.name = name;
        this.imageUris = imageUris;
        this.manaCost = manaCost;
        this.typeLine = typeLine;
        this.oracleText = oracleText;
        this.flavorText = flavorText;
        this.set = set;
        this.rarity = rarity;
        this.collectorNumber = collectorNumber;
        this.power = power;
        this.toughness = toughness;
        this.legalities = legalities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageUris getImageUris() {
        return imageUris;
    }

    public void setImageUris(ImageUris imageUris) {
        this.imageUris = imageUris;
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

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getCollectorNumber() {
        return collectorNumber;
    }

    public void setCollectorNumber(String collectorNumber) {
        this.collectorNumber = collectorNumber;
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

    public Legalities getLegalities() {
        return legalities;
    }

    public void setLegalities(Legalities legalities) {
        this.legalities = legalities;
    }
}