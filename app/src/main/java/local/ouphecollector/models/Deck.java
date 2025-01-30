package local.ouphecollector.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.sql.Timestamp;

import local.ouphecollector.utils.TimestampConverter;

@Entity(tableName = "deck")
public class Deck {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String format;
    @TypeConverters({TimestampConverter.class})
    private Timestamp created_at;


    // Constructor, getters, and setters
    public Deck(String name, String format, Timestamp created_at) {
        this.name = name;
        this.format = format;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}