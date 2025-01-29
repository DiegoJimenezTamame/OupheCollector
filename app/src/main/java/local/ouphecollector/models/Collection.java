package local.ouphecollector.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Collection {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String card_id;
    private int quantity;
    private String condition;
    private boolean is_foil;
    private String language;
    private String set_code;

    // Constructor, getters, and setters
    public Collection(String card_id, int quantity, String condition, boolean is_foil, String language, String set_code) {
        this.card_id = card_id;
        this.quantity = quantity;
        this.condition = condition;
        this.is_foil = is_foil;
        this.language = language;
        this.set_code = set_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isIs_foil() {
        return is_foil;
    }

    public void setIs_foil(boolean is_foil) {
        this.is_foil = is_foil;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSet_code() {
        return set_code;
    }

    public void setSet_code(String set_code) {
        this.set_code = set_code;
    }
}