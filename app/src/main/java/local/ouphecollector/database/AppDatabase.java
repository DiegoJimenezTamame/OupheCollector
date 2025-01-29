package local.ouphecollector.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import local.ouphecollector.database.dao.CardDao;
import local.ouphecollector.models.Card;
import local.ouphecollector.models.Deck;
import local.ouphecollector.models.Collection;
import local.ouphecollector.models.Profile;
import local.ouphecollector.models.Wishlist;
import local.ouphecollector.database.dao.CollectionDao;
import local.ouphecollector.database.dao.DeckDao;
import local.ouphecollector.database.dao.ProfileDao;
import local.ouphecollector.database.dao.WishlistDao;

@Database(entities = {Card.class, Collection.class, Deck.class, Profile.class, Wishlist.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CardDao cardDao();
    public abstract CollectionDao collectionDao();
    public abstract DeckDao deckDao();
    public abstract ProfileDao profileDao();
    public abstract WishlistDao wishlistDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "mtg_collector_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}