package local.ouphecollector.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import local.ouphecollector.models.ImageUris;
import local.ouphecollector.models.Legalities;
import local.ouphecollector.models.RelatedCard;
import local.ouphecollector.models.RelatedUris;

public class Converters {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<String> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<String> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static List<RelatedCard> stringToRelatedCardList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<RelatedCard>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String relatedCardListToString(List<RelatedCard> relatedCards) {
        return gson.toJson(relatedCards);
    }

    @TypeConverter
    public static String fromLegalities(Legalities legalities) {
        if (legalities == null) {
            return null;
        }
        return gson.toJson(legalities);
    }

    @TypeConverter
    public static Legalities toLegalities(String legalitiesString) {
        if (legalitiesString == null) {
            return null;
        }
        Type type = new TypeToken<Legalities>() {}.getType();
        return gson.fromJson(legalitiesString, type);
    }

    @TypeConverter
    public static String fromImageUris(ImageUris imageUris) {
        if (imageUris == null) {
            return null;
        }
        return gson.toJson(imageUris);
    }

    @TypeConverter
    public static ImageUris toImageUris(String imageUrisString) {
        if (imageUrisString == null) {
            return null;
        }
        Type type = new TypeToken<ImageUris>() {}.getType();
        return gson.fromJson(imageUrisString, type);
    }

    @TypeConverter
    public static String fromRelatedUris(RelatedUris relatedUris) {
        if (relatedUris == null) {
            return null;
        }
        return gson.toJson(relatedUris);
    }

    @TypeConverter
    public static RelatedUris toRelatedUris(String relatedUrisString) {
        if (relatedUrisString == null) {
            return null;
        }
        Type type = new TypeToken<RelatedUris>() {}.getType();
        return gson.fromJson(relatedUrisString, type);
    }
}