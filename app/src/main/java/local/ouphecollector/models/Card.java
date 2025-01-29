package local.ouphecollector.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card {
    @PrimaryKey
    private String id; // Scryfall ID or other unique identifier
    private String name;
    private String setCode;
    // ... other card properties ...

    // Constructor, getters, and setters
    public Card(String id, String name, String setCode) {
        this.id = id;
        this.name = name;
        this.setCode = setCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }
}