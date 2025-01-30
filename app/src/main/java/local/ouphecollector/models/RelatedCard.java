package local.ouphecollector.models;

import com.google.gson.annotations.SerializedName;

public class RelatedCard {
    @SerializedName("id")
    private String id;
    @SerializedName("object")
    private String object;
    @SerializedName("component")
    private String component;
    @SerializedName("name")
    private String name;
    @SerializedName("type_line")
    private String typeLine;
    @SerializedName("uri")
    private String uri;

    // --- Getters and Setters ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeLine() {
        return typeLine;
    }

    public void setTypeLine(String typeLine) {
        this.typeLine = typeLine;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}