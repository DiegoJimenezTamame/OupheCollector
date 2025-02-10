package local.ouphecollector.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import local.ouphecollector.models.CardCollection;

@Dao
public interface CardCollectionDao {
    @Insert
    void insert(CardCollection cardCollection);

    @Update
    void update(CardCollection cardCollection);

    @Delete
    void delete(CardCollection cardCollection);

    @Query("SELECT * FROM card_collection_table WHERE collection_id = :collectionId")
    LiveData<List<CardCollection>> getCardCollectionsByCollectionId(String collectionId);

    @Query("SELECT * FROM card_collection_table")
    LiveData<List<CardCollection>> getAllCardCollections();

    @Query("SELECT * FROM card_collection_table WHERE id = :cardCollectionId")
    CardCollection getCardCollectionById(int cardCollectionId);

    @Query("SELECT * FROM card_collection_table WHERE card_id = :cardId")
    List<CardCollection> getCardCollectionsByCardId(String cardId);
}