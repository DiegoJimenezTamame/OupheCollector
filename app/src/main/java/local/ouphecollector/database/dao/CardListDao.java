package local.ouphecollector.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import local.ouphecollector.models.CardListEntity;

import java.util.List;

@Dao
public interface CardListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CardListEntity cardListEntity);

    @Query("SELECT * FROM card_list")
    LiveData<List<CardListEntity>> getAllCardLists();
}