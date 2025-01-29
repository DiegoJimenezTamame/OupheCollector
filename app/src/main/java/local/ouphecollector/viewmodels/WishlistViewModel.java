package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import local.ouphecollector.models.Wishlist;
import local.ouphecollector.repository.WishlistRepository;

import java.util.List;

public class WishlistViewModel extends AndroidViewModel {
    private WishlistRepository wishlistRepository;
    private LiveData<List<Wishlist>> allWishlists;

    public WishlistViewModel(Application application) {
        super(application);
        wishlistRepository = new WishlistRepository(application);
        allWishlists = new MutableLiveData<>();
        loadAllWishlists();
    }

    public LiveData<List<Wishlist>> getAllWishlists() {
        return allWishlists;
    }

    public void loadAllWishlists() {
        List<Wishlist> wishlists = wishlistRepository.getAllWishlists();
        ((MutableLiveData<List<Wishlist>>) allWishlists).setValue(wishlists);
    }

    public void insertWishlist(Wishlist wishlist) {
        wishlistRepository.insertWishlist(wishlist);
        loadAllWishlists();
    }

    public void updateWishlist(Wishlist wishlist) {
        wishlistRepository.updateWishlist(wishlist);
        loadAllWishlists();
    }

    public void deleteWishlist(Wishlist wishlist) {
        wishlistRepository.deleteWishlist(wishlist);
        loadAllWishlists();
    }
}