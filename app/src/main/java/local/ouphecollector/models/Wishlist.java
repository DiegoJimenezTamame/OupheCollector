package local.ouphecollector.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wishlist")
public class Wishlist {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String card_id;
    private int priority;
    private double target_price;

    // Constructor, getters, and setters
    public Wishlist(String card_id, int priority, double target_price) {
        this.card_id = card_id;
        this.priority = priority;
        this.target_price = target_price;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getTarget_price() {
        return target_price;
    }

    public void setTarget_price(double target_price) {
        this.target_price = target_price;
    }
}