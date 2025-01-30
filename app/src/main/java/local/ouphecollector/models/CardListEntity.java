package local.ouphecollector.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "card_list")
public class CardListEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String cardIds; // Store card IDs as a comma-separated string

    public CardListEntity(String cardIds) {
        this.cardIds = cardIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardIds() {
        return cardIds;
    }

    public void setCardIds(String cardIds) {
        this.cardIds = cardIds;
    }
}