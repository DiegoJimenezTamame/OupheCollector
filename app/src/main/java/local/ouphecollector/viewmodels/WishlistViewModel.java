package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import local.ouphecollector.models.Wishlist;
import local.ouphecollector.repositories.WishlistRepository;

import java.util.List;

public class WishlistViewModel extends AndroidViewModel {
    private WishlistRepository wishlistRepository;
    private LiveData<List<Wishlist>> allWishlists;

    public WishlistViewModel(Application application) {
        super(application);
        wishlistRepository = new WishlistRepository(application);
        allWishlists = wishlistRepository.getAllWishlists();
    }

    public LiveData<List<Wishlist>> getAllWishlists() {
        return allWishlists;
    }

    public void insertWishlist(Wishlist wishlist) {
        wishlistRepository.insertWishlist(wishlist);
    }

    public void updateWishlist(Wishlist wishlist) {
        wishlistRepository.updateWishlist(wishlist);
    }

    public void deleteWishlist(Wishlist wishlist) {
        wishlistRepository.deleteWishlist(wishlist);
    }
}