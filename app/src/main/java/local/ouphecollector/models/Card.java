package local.ouphecollector.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Card implements Parcelable {

    @NonNull
    @PrimaryKey
    public String id;

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
    private String setCode;

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

    @SerializedName("set_name")
    private String expansionName;

    @SerializedName("collector_number")
    private String collectorNumber;

    public Card() {
        id = "";
    }

    public String getCollectorNumber() {
        return collectorNumber;
    }

    public void setCollectorNumber(String collectorNumber) {
        this.collectorNumber = collectorNumber;
    }

    public String getExpansionName() {
        return expansionName;
    }

    public void setExpansionName(String expansionName) {
        this.expansionName = expansionName;
    }

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

    public void setId (@NonNull String id) {
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

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.manaCost);
        dest.writeString(this.typeLine);
        dest.writeString(this.oracleText);
        dest.writeString(this.power);
        dest.writeString(this.toughness);
        dest.writeStringList(this.colors);
        dest.writeStringList(this.colorIdentity);
        dest.writeString(this.artist);
        dest.writeString(this.rarity);
        dest.writeString(this.setCode);
        dest.writeString(this.flavorText);
        dest.writeParcelable(this.imageUris, flags);
        dest.writeParcelable(this.legalities, flags);
        dest.writeParcelable(this.relatedUris, flags);
        dest.writeList(this.allParts);
        dest.writeString(this.printsSearchUri);
        dest.writeString(this.expansionName);
        dest.writeString(this.collectorNumber);
    }

    public void readFromParcel(Parcel source) {
        this.id = Objects.requireNonNull(source.readString());
        this.name = source.readString();
        this.manaCost = source.readString();
        this.typeLine = source.readString();
        this.oracleText = source.readString();
        this.power = source.readString();
        this.toughness = source.readString();
        this.colors = source.createStringArrayList();
        this.colorIdentity = source.createStringArrayList();
        this.artist = source.readString();
        this.rarity = source.readString();
        this.setCode = source.readString();
        this.flavorText = source.readString();
        this.imageUris = source.readParcelable(ImageUris.class.getClassLoader());
        this.legalities = source.readParcelable(Legalities.class.getClassLoader());
        this.relatedUris = source.readParcelable(RelatedUris.class.getClassLoader());
        this.allParts = new ArrayList<>();
        source.readList(this.allParts, RelatedCard.class.getClassLoader());
        this.printsSearchUri = source.readString();
        this.expansionName = source.readString();
        this.collectorNumber = source.readString();
    }

    @Ignore
    protected Card(Parcel in) {
        this.id = Objects.requireNonNull(in.readString());
        this.name = in.readString();
        this.manaCost = in.readString();
        this.typeLine = in.readString();
        this.oracleText = in.readString();
        this.power = in.readString();
        this.toughness = in.readString();
        this.colors = in.createStringArrayList();
        this.colorIdentity = in.createStringArrayList();
        this.artist = in.readString();
        this.rarity = in.readString();
        this.setCode = in.readString();
        this.flavorText = in.readString();
        this.imageUris = in.readParcelable(ImageUris.class.getClassLoader());
        this.legalities = in.readParcelable(Legalities.class.getClassLoader());
        this.relatedUris = in.readParcelable(RelatedUris.class.getClassLoader());
        this.allParts = new ArrayList<>();
        in.readList(this.allParts, RelatedCard.class.getClassLoader());
        this.printsSearchUri = in.readString();
        this.expansionName = in.readString();
        this.collectorNumber = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
    }

