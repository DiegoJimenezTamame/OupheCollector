package local.ouphecollector.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import local.ouphecollector.models.Wishlist;

import java.util.List;

@Dao
public interface WishlistDao {
    @Insert
    void insert(Wishlist wishlist);

    @Update
    void update(Wishlist wishlist);

    @Delete
    void delete(Wishlist wishlist);

    @Query("SELECT * FROM Wishlist")
    LiveData<List<Wishlist>> getAllWishlists(); // Changed to LiveData

    @Query("SELECT * FROM Wishlist WHERE id = :wishlistId")
    Wishlist getWishlistById(int wishlistId);

    @Query("SELECT * FROM Wishlist WHERE card_id = :cardId")
    List<Wishlist> getWishlistsByCardId(String cardId);
}