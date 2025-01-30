package local.ouphecollector.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import local.ouphecollector.utils.TimestampConverter;

@Database(entities = {Card.class, Collection.class, Deck.class, Profile.class, Wishlist.class}, version = 1, exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CardDao cardDao();
    public abstract CollectionDao collectionDao();
    public abstract DeckDao deckDao();
    public abstract ProfileDao profileDao();
    public abstract WishlistDao wishlistDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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