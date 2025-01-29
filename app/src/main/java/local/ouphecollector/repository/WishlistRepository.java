package local.ouphecollector.repository;

import android.app.Application;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.WishlistDao;
import local.ouphecollector.models.Wishlist;

import java.util.List;

public class WishlistRepository {
    private WishlistDao wishlistDao;

    public WishlistRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        wishlistDao = db.wishlistDao();
    }

    public List<Wishlist> getAllWishlists() {
        return wishlistDao.getAllWishlists();
    }

    public Wishlist getWishlistById(int wishlistId) {
        return wishlistDao.getWishlistById(wishlistId);
    }

    public List<Wishlist> getWishlistsByCardId(String cardId) {
        return wishlistDao.getWishlistsByCardId(cardId);
    }

    public void insertWishlist(Wishlist wishlist) {
        wishlistDao.insert(wishlist);
    }

    public void updateWishlist(Wishlist wishlist) {
        wishlistDao.update(wishlist);
    }

    public void deleteWishlist(Wishlist wishlist) {
        wishlistDao.delete(wishlist);
    }
}