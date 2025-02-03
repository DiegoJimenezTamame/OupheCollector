package local.ouphecollector.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageUris implements Parcelable {
    @SerializedName("small")
    private String small;
    @SerializedName("normal")
    private String normal;
    @SerializedName("large")
    private String large;
    @SerializedName("png")
    private String png;
    @SerializedName("art_crop")
    private String artCrop;
    @SerializedName("border_crop")
    private String borderCrop;

    // --- Getters and Setters ---

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public String getArtCrop() {
        return artCrop;
    }

    public void setArtCrop(String artCrop) {
        this.artCrop = artCrop;
    }

    public String getBorderCrop() {
        return borderCrop;
    }

    public void setBorderCrop(String borderCrop) {
        this.borderCrop = borderCrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.small);
        dest.writeString(this.normal);
        dest.writeString(this.large);
        dest.writeString(this.png);
        dest.writeString(this.artCrop);
        dest.writeString(this.borderCrop);
    }

    public void readFromParcel(Parcel source) {
        this.small = source.readString();
        this.normal = source.readString();
        this.large = source.readString();
        this.png = source.readString();
        this.artCrop = source.readString();
        this.borderCrop = source.readString();
    }

    public ImageUris() {
    }

    protected ImageUris(Parcel in) {
        this.small = in.readString();
        this.normal = in.readString();
        this.large = in.readString();
        this.png = in.readString();
        this.artCrop = in.readString();
        this.borderCrop = in.readString();
    }

    public static final Parcelable.Creator<ImageUris> CREATOR = new Parcelable.Creator<>() {
        @Override
        public ImageUris createFromParcel(Parcel source) {
            return new ImageUris(source);
        }

        @Override
        public ImageUris[] newArray(int size) {
            return new ImageUris[size];
        }
    };
}