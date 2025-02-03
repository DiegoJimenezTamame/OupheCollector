package local.ouphecollector.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RelatedUris implements Parcelable {
    @SerializedName("gatherer")
    private String gatherer;
    @SerializedName("tcgplayer_infinite_articles")
    private String tcgplayerInfiniteArticles;
    @SerializedName("tcgplayer_infinite_decks")
    private String tcgplayerInfiniteDecks;
    @SerializedName("edhrec")
    private String edhrec;

    // --- Getters and Setters ---

    public String getGatherer() {
        return gatherer;
    }

    public void setGatherer(String gatherer) {
        this.gatherer = gatherer;
    }

    public String getTcgplayerInfiniteArticles() {
        return tcgplayerInfiniteArticles;
    }

    public void setTcgplayerInfiniteArticles(String tcgplayerInfiniteArticles) {
        this.tcgplayerInfiniteArticles = tcgplayerInfiniteArticles;
    }

    public String getTcgplayerInfiniteDecks() {
        return tcgplayerInfiniteDecks;
    }

    public void setTcgplayerInfiniteDecks(String tcgplayerInfiniteDecks) {
        this.tcgplayerInfiniteDecks = tcgplayerInfiniteDecks;
    }

    public String getEdhrec() {
        return edhrec;
    }

    public void setEdhrec(String edhrec) {
        this.edhrec = edhrec;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gatherer);
        dest.writeString(this.tcgplayerInfiniteArticles);
        dest.writeString(this.tcgplayerInfiniteDecks);
        dest.writeString(this.edhrec);
    }

    public void readFromParcel(Parcel source) {
        this.gatherer = source.readString();
        this.tcgplayerInfiniteArticles = source.readString();
        this.tcgplayerInfiniteDecks = source.readString();
        this.edhrec = source.readString();
    }

    public RelatedUris() {
    }

    protected RelatedUris(Parcel in) {
        this.gatherer = in.readString();
        this.tcgplayerInfiniteArticles = in.readString();
        this.tcgplayerInfiniteDecks = in.readString();
        this.edhrec = in.readString();
    }

    public static final Parcelable.Creator<RelatedUris> CREATOR = new Parcelable.Creator<>() {
        @Override
        public RelatedUris createFromParcel(Parcel source) {
            return new RelatedUris(source);
        }

        @Override
        public RelatedUris[] newArray(int size) {
            return new RelatedUris[size];
        }
    };
}