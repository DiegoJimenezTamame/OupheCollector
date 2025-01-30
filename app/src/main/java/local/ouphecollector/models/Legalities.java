package local.ouphecollector.models;

public class Legalities {
    private String standard;
    private String future;
    private String historic;
    private String gladiator;
    private String pioneer;
    private String explorer;
    private String modern;
    private String legacy;
    private String pauper;
    private String vintage;
    private String penny;
    private String commander;
    private String brawl;
    private String duel;
    private String oldschool;
    private String premodern;

    // Constructor (optional)
    public Legalities(String standard, String future, String historic, String gladiator, String pioneer, String explorer, String modern, String legacy, String pauper, String vintage, String penny, String commander, String brawl, String duel, String oldschool, String premodern) {
        this.standard = standard;
        this.future = future;
        this.historic = historic;
        this.gladiator = gladiator;
        this.pioneer = pioneer;
        this.explorer = explorer;
        this.modern = modern;
        this.legacy = legacy;
        this.pauper = pauper;
        this.vintage = vintage;
        this.penny = penny;
        this.commander = commander;
        this.brawl = brawl;
        this.duel = duel;
        this.oldschool = oldschool;
        this.premodern = premodern;
    }

    // Getters
    public String getStandard() {
        return standard;
    }

    public String getFuture() {
        return future;
    }

    public String getHistoric() {
        return historic;
    }

    public String getGladiator() {
        return gladiator;
    }

    public String getPioneer() {
        return pioneer;
    }

    public String getExplorer() {
        return explorer;
    }

    public String getModern() {
        return modern;
    }

    public String getLegacy() {
        return legacy;
    }

    public String getPauper() {
        return pauper;
    }

    public String getVintage() {
        return vintage;
    }

    public String getPenny() {
        return penny;
    }

    public String getCommander() {
        return commander;
    }

    public String getBrawl() {
        return brawl;
    }

    public String getDuel() {
        return duel;
    }

    public String getOldschool() {
        return oldschool;
    }

    public String getPremodern() {
        return premodern;
    }

    // Setters (optional, but needed if you want to modify the fields after creation)
    public void setStandard(String standard) {
        this.standard = standard;
    }

    public void setFuture(String future) {
        this.future = future;
    }

    public void setHistoric(String historic) {
        this.historic = historic;
    }

    public void setGladiator(String gladiator) {
        this.gladiator = gladiator;
    }

    public void setPioneer(String pioneer) {
        this.pioneer = pioneer;
    }

    public void setExplorer(String explorer) {
        this.explorer = explorer;
    }

    public void setModern(String modern) {
        this.modern = modern;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public void setPauper(String pauper) {
        this.pauper = pauper;
    }

    public void setVintage(String vintage) {
        this.vintage = vintage;
    }

    public void setPenny(String penny) {
        this.penny = penny;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public void setBrawl(String brawl) {
        this.brawl = brawl;
    }

    public void setDuel(String duel) {
        this.duel = duel;
    }

    public void setOldschool(String oldschool) {
        this.oldschool = oldschool;
    }

    public void setPremodern(String premodern) {
        this.premodern = premodern;
    }
}