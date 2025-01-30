package local.ouphecollector.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import local.ouphecollector.models.Card;

@Dao
public interface CardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Card card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);

    @Query("SELECT * FROM Card")
    LiveData<List<Card>> getAllCards();

    @Query("SELECT * FROM Card WHERE id = :cardId")
    Card getCardById(String cardId);
}