package local.ouphecollector.database.dao;

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

    @Query("SELECT * FROM Deck")
    List<Deck> getAllDecks();

    @Query("SELECT * FROM Deck WHERE id = :deckId")
    Deck getDeckById(int deckId);
}