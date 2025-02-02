package local.ouphecollector.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import local.ouphecollector.R;
import local.ouphecollector.databinding.ActivityMainBinding;
import local.ouphecollector.databinding.AppBarMainBinding;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private ActivityMainBinding binding;
    private AppBarMainBinding appBarMainBinding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        // View Binding setup
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        appBarMainBinding = AppBarMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the toolbar
        Toolbar toolbar = appBarMainBinding.toolbar;
        setSupportActionBar(toolbar);

        // Set up Navigation Drawer
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Set up Navigation Controller
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        // Link the action bar and the navigation drawer
        if (navController != null) {
            NavigationUI.setupWithNavController(navigationView, navController);
        }
        setMenuIcons(navigationView);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private Map<Integer, Integer> getMenuImages() {
        Map<Integer, Integer> menuImages = new HashMap<>();
        menuImages.put(R.id.nav_home, R.drawable.home_icon);
        menuImages.put(R.id.nav_collection, R.drawable.collection_icon);
        menuImages.put(R.id.nav_scanner, R.drawable.scanner_icon);
        menuImages.put(R.id.nav_decklist, R.drawable.decklist_icon);
        menuImages.put(R.id.nav_wishlist, R.drawable.shopping_cart_icon);
        menuImages.put(R.id.nav_profile, R.drawable.profile_icon);
        menuImages.put(R.id.nav_search, R.drawable.search_icon);
        return menuImages;
    }

    private void setMenuIcons(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        Map<Integer, Integer> menuImages = getMenuImages();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            Integer imageResId = menuImages.get(menuItem.getItemId());
            if (imageResId != null) {
                Drawable icon = ContextCompat.getDrawable(this, imageResId);
                menuItem.setIcon(icon);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}