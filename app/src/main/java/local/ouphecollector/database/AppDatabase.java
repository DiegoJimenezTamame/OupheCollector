package local.ouphecollector.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import local.ouphecollector.database.dao.CardCollectionDao;
import local.ouphecollector.database.dao.CardDao;
import local.ouphecollector.database.dao.CardListDao;
import local.ouphecollector.database.dao.CollectionDao;
import local.ouphecollector.database.dao.DeckDao;
import local.ouphecollector.database.dao.ProfileDao;
import local.ouphecollector.database.dao.WishlistDao;
import local.ouphecollector.models.Card;
import local.ouphecollector.models.CardCollection;
import local.ouphecollector.models.CardListEntity;
import local.ouphecollector.models.Collection;
import local.ouphecollector.models.Deck;
import local.ouphecollector.models.Profile;
import local.ouphecollector.models.Wishlist;
import local.ouphecollector.utils.Converters;

@Database(entities = {Card.class, CardListEntity.class, Collection.class, CardCollection.class, Deck.class, Profile.class, Wishlist.class}, version = 5, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CardDao cardDao();
    public abstract CardListDao cardListDao();
    public abstract CollectionDao collectionDao();
    public abstract CardCollectionDao cardCollectionDao();
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
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}