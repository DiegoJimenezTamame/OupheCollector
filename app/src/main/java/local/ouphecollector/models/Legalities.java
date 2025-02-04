package local.ouphecollector.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Legalities implements Parcelable {
    @SerializedName("standard")
    private String standard;
    @SerializedName("future")
    private String future;
    @SerializedName("historic")
    private String historic;
    @SerializedName("gladiator")
    private String gladiator;
    @SerializedName("pioneer")
    private String pioneer;
    @SerializedName("explorer")
    private String explorer;
    @SerializedName("modern")
    private String modern;
    @SerializedName("legacy")
    private String legacy;
    @SerializedName("pauper")
    private String pauper;
    @SerializedName("vintage")
    private String vintage;
    @SerializedName("penny")
    private String penny;
    @SerializedName("commander")
    private String commander;
    @SerializedName("brawl")
    private String brawl;
    @SerializedName("historicbrawl")
    private String historicBrawl;
    @SerializedName("alchemy")
    private String alchemy;
    @SerializedName("paupercommander")
    private String pauperCommander;
    @SerializedName("duel")
    private String duel;
    @SerializedName("oldschool")
    private String oldschool;
    @SerializedName("premodern")
    private String premodern;

    public String getOathbreaker() {
        return oathbreaker;
    }

    public void setOathbreaker(String oathbreaker) {
        this.oathbreaker = oathbreaker;
    }

    public String getPredh() {
        return predh;
    }

    public void setPredh(String predh) {
        this.predh = predh;
    }

    public String getPredh2() {
        return predh2;
    }

    public void setPredh2(String predh2) {
        this.predh2 = predh2;
    }

    @SerializedName("oathbreaker")
    private String oathbreaker;
    @SerializedName("predh")
    private String predh;
    @SerializedName("predh2")
    private String predh2;




    // --- Getters and Setters ---

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getFuture() {
        return future;
    }

    public void setFuture(String future) {
        this.future = future;
    }

    public String getHistoric() {
        return historic;
    }

    public void setHistoric(String historic) {
        this.historic = historic;
    }

    public String getGladiator() {
        return gladiator;
    }

    public void setGladiator(String gladiator) {
        this.gladiator = gladiator;
    }

    public String getPioneer() {
        return pioneer;
    }

    public void setPioneer(String pioneer) {
        this.pioneer = pioneer;
    }

    public String getExplorer() {
        return explorer;
    }

    public void setExplorer(String explorer) {
        this.explorer = explorer;
    }

    public String getModern() {
        return modern;
    }

    public void setModern(String modern) {
        this.modern = modern;
    }

    public String getLegacy() {
        return legacy;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public String getPauper() {
        return pauper;
    }

    public void setPauper(String pauper) {
        this.pauper = pauper;
    }

    public String getVintage() {
        return vintage;
    }

    public void setVintage(String vintage) {
        this.vintage = vintage;
    }

    public String getPenny() {
        return penny;
    }

    public void setPenny(String penny) {
        this.penny = penny;
    }

    public String getCommander() {
        return commander;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public String getBrawl() {
        return brawl;
    }

    public void setBrawl(String brawl) {
        this.brawl = brawl;
    }

    public String getHistoricBrawl() {
        return historicBrawl;
    }

    public void setHistoricBrawl(String historicBrawl) {
        this.historicBrawl = historicBrawl;
    }

    public String getAlchemy() {
        return alchemy;
    }

    public void setAlchemy(String alchemy) {
        this.alchemy = alchemy;
    }

    public String getPauperCommander() {
        return pauperCommander;
    }

    public void setPauperCommander(String pauperCommander) {
        this.pauperCommander = pauperCommander;
    }

    public String getDuel() {
        return duel;
    }

    public void setDuel(String duel) {
        this.duel = duel;
    }

    public String getOldschool() {
        return oldschool;
    }

    public void setOldschool(String oldschool) {
        this.oldschool = oldschool;
    }

    public String getPremodern() {
        return premodern;
    }

    public void setPremodern(String premodern) {
        this.premodern = premodern;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.standard);
        dest.writeString(this.future);
        dest.writeString(this.historic);
        dest.writeString(this.gladiator);
        dest.writeString(this.pioneer);
        dest.writeString(this.explorer);
        dest.writeString(this.modern);
        dest.writeString(this.legacy);
        dest.writeString(this.pauper);
        dest.writeString(this.vintage);
        dest.writeString(this.penny);
        dest.writeString(this.commander);
        dest.writeString(this.brawl);
        dest.writeString(this.historicBrawl);
        dest.writeString(this.alchemy);
        dest.writeString(this.pauperCommander);
        dest.writeString(this.duel);
        dest.writeString(this.oldschool);
        dest.writeString(this.premodern);
    }

    public void readFromParcel(Parcel source) {
        this.standard = source.readString();
        this.future = source.readString();
        this.historic = source.readString();
        this.gladiator = source.readString();
        this.pioneer = source.readString();
        this.explorer = source.readString();
        this.modern = source.readString();
        this.legacy = source.readString();
        this.pauper = source.readString();
        this.vintage = source.readString();
        this.penny = source.readString();
        this.commander = source.readString();
        this.brawl = source.readString();
        this.historicBrawl = source.readString();
        this.alchemy = source.readString();
        this.pauperCommander = source.readString();
        this.duel = source.readString();
        this.oldschool = source.readString();
        this.premodern = source.readString();
    }

    public Legalities() {
    }

    protected Legalities(Parcel in) {
        this.standard = in.readString();
        this.future = in.readString();
        this.historic = in.readString();
        this.gladiator = in.readString();
        this.pioneer = in.readString();
        this.explorer = in.readString();
        this.modern = in.readString();
        this.legacy = in.readString();
        this.pauper = in.readString();
        this.vintage = in.readString();
        this.penny = in.readString();
        this.commander = in.readString();
        this.brawl = in.readString();
        this.historicBrawl = in.readString();
        this.alchemy = in.readString();
        this.pauperCommander = in.readString();
        this.duel = in.readString();
        this.oldschool = in.readString();
        this.premodern = in.readString();
    }

    public static final Parcelable.Creator<Legalities> CREATOR = new Parcelable.Creator<Legalities>() {
        @Override
        public Legalities createFromParcel(Parcel source) {
            return new Legalities(source);
        }

        @Override
        public Legalities[] newArray(int size) {
            return new Legalities[size];
        }
    };
}