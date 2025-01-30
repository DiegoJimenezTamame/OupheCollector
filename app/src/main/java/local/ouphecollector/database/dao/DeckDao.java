package local.ouphecollector.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import local.ouphecollector.models.Deck;

import java.util.List;

@Dao
public interface DeckDao {
    @Insert
    void insert(Deck deck);

    @Update
    void update(Deck deck);

    @Delete
    void delete(Deck deck);

    @Query("SELECT * FROM deck") // Changed to "deck"
    LiveData<List<Deck>> getAllDecks();

    @Query("SELECT * FROM deck WHERE id = :deckId") // Changed to "deck"
    Deck getDeckById(int deckId);
}