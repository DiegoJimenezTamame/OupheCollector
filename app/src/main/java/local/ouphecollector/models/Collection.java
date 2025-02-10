package local.ouphecollector.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "collection_table")
public class Collection {
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String tag;
    @ColumnInfo(name = "created_at")
    private Date createdAt;
    @ColumnInfo(name = "last_modified")
    private Date lastModified;

    @Ignore // Ignore this constructor
    public Collection(String name, String tag) {
        this.name = name;
        this.tag = tag;
        this.id = UUID.randomUUID().toString();
        this.createdAt = new Date();
        this.lastModified = new Date();
    }

    public Collection() {
        // No-arg constructor is now the only one Room will use
        id = "";
    }

    @NonNull
    public String getId() {
        return id;
    }


    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.lastModified = new Date();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
        this.lastModified = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}