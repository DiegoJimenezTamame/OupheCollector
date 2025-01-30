package local.ouphecollector.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.WishlistDao;
import local.ouphecollector.models.Wishlist;

import java.util.List;

public class WishlistRepository {
    private WishlistDao wishlistDao;
    private LiveData<List<Wishlist>> allWishlists;

    public WishlistRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        wishlistDao = db.wishlistDao();
        allWishlists = wishlistDao.getAllWishlists();
    }

    public LiveData<List<Wishlist>> getAllWishlists() {
        return allWishlists; // Changed to LiveData
    }

    public Wishlist getWishlistById(int wishlistId) {
        return wishlistDao.getWishlistById(wishlistId);
    }

    public List<Wishlist> getWishlistsByCardId(String cardId) {
        return wishlistDao.getWishlistsByCardId(cardId);
    }

    public void insertWishlist(Wishlist wishlist) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            wishlistDao.insert(wishlist);
        });
    }

    public void updateWishlist(Wishlist wishlist) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            wishlistDao.update(wishlist);
        });
    }

    public void deleteWishlist(Wishlist wishlist) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            wishlistDao.delete(wishlist);
        });
    }
}