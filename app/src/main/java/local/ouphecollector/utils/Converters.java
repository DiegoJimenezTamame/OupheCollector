package local.ouphecollector.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import local.ouphecollector.models.ImageUris;
import local.ouphecollector.models.Legalities;
import local.ouphecollector.models.RelatedCard;
import local.ouphecollector.models.RelatedUris;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    // ImageUris
    @TypeConverter
    public static String fromImageUris(ImageUris imageUris) {
        return gson.toJson(imageUris);
    }

    @TypeConverter
    public static ImageUris toImageUris(String imageUrisString) {
        Type type = new TypeToken<ImageUris>() {}.getType();
        return gson.fromJson(imageUrisString, type);
    }

    // List<String>
    @TypeConverter
    public static String fromStringList(List<String> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<String> toStringList(String listString) {
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(listString, type);
    }

    // List<RelatedCard>
    @TypeConverter
    public static String fromRelatedCardList(List<RelatedCard> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<RelatedCard> toRelatedCardList(String listString) {
        Type type = new TypeToken<List<RelatedCard>>() {}.getType();
        return gson.fromJson(listString, type);
    }

    // RelatedUris
    @TypeConverter
    public static String fromRelatedUris(RelatedUris relatedUris) {
        return gson.toJson(relatedUris);
    }

    @TypeConverter
    public static RelatedUris toRelatedUris(String relatedUrisString) {
        Type type = new TypeToken<RelatedUris>() {}.getType();
        return gson.fromJson(relatedUrisString, type);
    }

    // Legalities
    @TypeConverter
    public static String fromLegalities(Legalities legalities) {
        return gson.toJson(legalities);
    }

    @TypeConverter
    public static Legalities toLegalities(String legalitiesString) {
        Type type = new TypeToken<Legalities>() {}.getType();
        return gson.fromJson(legalitiesString, type);
    }
}