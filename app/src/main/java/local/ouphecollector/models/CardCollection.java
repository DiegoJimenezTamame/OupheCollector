package local.ouphecollector.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "card_collection_table",
        foreignKeys = @ForeignKey(entity = Collection.class,
                parentColumns = "id",
                childColumns = "collection_id",
                onDelete = ForeignKey.CASCADE))
public class CardCollection {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "collection_id")
    private int collection_id;

    @ColumnInfo(name = "card_id")
    private String card_id;

    private int quantity;
    private String condition;

    @ColumnInfo(name = "is_foil")
    private boolean is_foil;

    private String language;

    @ColumnInfo(name = "set_code")
    private String set_code;

    public CardCollection(int collection_id, String card_id, int quantity, String condition, boolean is_foil, String language, String set_code) {
        this.collection_id = collection_id;
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

    public int getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(int collection_id) {
        this.collection_id = collection_id;
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