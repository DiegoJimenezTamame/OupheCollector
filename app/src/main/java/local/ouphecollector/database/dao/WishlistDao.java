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

    @Query("SELECT * FROM wishlist") // Changed to "wishlist"
    LiveData<List<Wishlist>> getAllWishlists();

    @Query("SELECT * FROM wishlist WHERE id = :wishlistId") // Changed to "wishlist"
    Wishlist getWishlistById(int wishlistId);

    @Query("SELECT * FROM wishlist WHERE card_id = :cardId") // Changed to "wishlist"
    List<Wishlist> getWishlistsByCardId(String cardId);
}