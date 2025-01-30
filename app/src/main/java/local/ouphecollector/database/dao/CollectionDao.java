package local.ouphecollector.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import local.ouphecollector.models.Collection;

import java.util.List;

@Dao
public interface CollectionDao {
    @Insert
    void insert(Collection collection);

    @Update
    void update(Collection collection);

    @Delete
    void delete(Collection collection);

    @Query("SELECT * FROM Collection")
    LiveData<List<Collection>> getAllCollections(); // Changed to LiveData

    @Query("SELECT * FROM Collection WHERE id = :collectionId")
    Collection getCollectionById(int collectionId);

    @Query("SELECT * FROM Collection WHERE card_id = :cardId")
    List<Collection> getCollectionsByCardId(String cardId);
}