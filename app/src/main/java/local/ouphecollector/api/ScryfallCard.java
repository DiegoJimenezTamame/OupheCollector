package local.ouphecollector.api;

import com.google.gson.annotations.SerializedName;

public class ScryfallCard {
    private String object;
    private String id;
    private String name;
    private String set;
    @SerializedName("set_name")
    private String setName;
    @SerializedName("image_uris")
    private ScryfallImageUris imageUris;

    // Constructor, getters, and setters
    public ScryfallCard(String object, String id, String name, String set, String setName, ScryfallImageUris imageUris) {
        this.object = object;
        this.id = id;
        this.name = name;
        this.set = set;
        this.setName = setName;
        this.imageUris = imageUris;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public ScryfallImageUris getImageUris() {
        return imageUris;
    }

    public void setImageUris(ScryfallImageUris imageUris) {
        this.imageUris = imageUris;
    }
}