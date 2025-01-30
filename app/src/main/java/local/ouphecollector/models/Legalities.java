package local.ouphecollector.models;

import com.google.gson.annotations.SerializedName;

public class Legalities {
    private String standard;
    private String future;
    private String historic;
    private String timeless;
    private String gladiator;
    private String pioneer;
    private String explorer;
    private String modern;
    private String legacy;
    private String pauper;
    private String vintage;
    private String penny;
    private String commander;
    private String oathbreaker;
    @SerializedName("standardbrawl")
    private String standardBrawl;
    private String brawl;
    private String alchemy;
    @SerializedName("paupercommander")
    private String pauperCommander;
    private String duel;
    private String oldschool;
    private String premodern;
    private String predh;

    public Legalities(String standard, String future, String historic, String timeless, String gladiator, String pioneer, String explorer, String modern, String legacy, String pauper, String vintage, String penny, String commander, String oathbreaker, String standardBrawl, String brawl, String alchemy, String pauperCommander, String duel, String oldschool, String premodern, String predh) {
        this.standard = standard;
        this.future = future;
        this.historic = historic;
        this.timeless = timeless;
        this.gladiator = gladiator;
        this.pioneer = pioneer;
        this.explorer = explorer;
        this.modern = modern;
        this.legacy = legacy;
        this.pauper = pauper;
        this.vintage = vintage;
        this.penny = penny;
        this.commander = commander;
        this.oathbreaker = oathbreaker;
        this.standardBrawl = standardBrawl;
        this.brawl = brawl;
        this.alchemy = alchemy;
        this.pauperCommander = pauperCommander;
        this.duel = duel;
        this.oldschool = oldschool;
        this.premodern = premodern;
        this.predh = predh;
    }

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

    public String getTimeless() {
        return timeless;
    }

    public void setTimeless(String timeless) {
        this.timeless = timeless;
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

    public String getOathbreaker() {
        return oathbreaker;
    }

    public void setOathbreaker(String oathbreaker) {
        this.oathbreaker = oathbreaker;
    }

    public String getStandardBrawl() {
        return standardBrawl;
    }

    public void setStandardBrawl(String standardBrawl) {
        this.standardBrawl = standardBrawl;
    }

    public String getBrawl() {
        return brawl;
    }

    public void setBrawl(String brawl) {
        this.brawl = brawl;
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

    public String getPredh() {
        return predh;
    }

    public void setPredh(String predh) {
        this.predh = predh;
    }
}