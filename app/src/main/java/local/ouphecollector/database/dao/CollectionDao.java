package local.ouphecollector.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import local.ouphecollector.models.Collection;

@Dao
public interface CollectionDao {
    @Insert
    void insert(Collection collection);

    @Update
    void update(Collection collection);

    @Delete
    void delete(Collection collection);

    @Query("SELECT * FROM collection_table")
    LiveData<List<Collection>> getAllCollections();

    @Query("SELECT * FROM collection_table WHERE id = :collectionId")
    Collection getCollectionById(int collectionId);
}