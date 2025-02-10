package local.ouphecollector.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "card_collection_table",
        foreignKeys = @ForeignKey(entity = Collection.class,
                parentColumns = "id",
                childColumns = "collection_id",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("collection_id")})
public class CardCollection {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "collection_id")
    private String collection_id;

    @ColumnInfo(name = "card_id")
    private String card_id;

    private int quantity;
    private String condition;

    @ColumnInfo(name = "is_foil")
    private boolean isFoil;

    private String language;

    @ColumnInfo(name = "set_code")
    private String set_code;

    public CardCollection(String collection_id, String card_id, int quantity, String condition, boolean isFoil, String language, String set_code) {
        this.collection_id = collection_id;
        this.card_id = card_id;
        this.quantity = quantity;
        this.condition = condition;
        this.isFoil = isFoil;
        this.language = language;
        this.set_code = set_code;
    }

    @Ignore
    public CardCollection() {
    }

    // ... (rest of your getters and setters)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(String collection_id) {
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

    public boolean isFoil() {
        return isFoil;
    }

    public void setFoil(boolean foil) {
        isFoil = foil;
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