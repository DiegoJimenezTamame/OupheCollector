package local.ouphecollector.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

@Database(entities = {Card.class, CardListEntity.class, Collection.class, CardCollection.class, Deck.class, Profile.class, Wishlist.class}, version = 7, exportSchema = false) // Changed version to 7
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

    // Add the migration here
    public static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Migrate Collection table
            database.execSQL("CREATE TABLE collection_temp (id TEXT PRIMARY KEY NOT NULL, name TEXT, tag TEXT, created_at INTEGER, last_modified INTEGER)");
            database.execSQL("INSERT INTO collection_temp (id, name, tag, created_at, last_modified) SELECT CAST(id AS TEXT), name, tag, created_at, last_modified FROM collection_table");
            database.execSQL("DROP TABLE collection_table");
            database.execSQL("ALTER TABLE collection_temp RENAME TO collection_table");

            // Migrate Card table
            database.execSQL("CREATE TABLE Card_temp (id TEXT PRIMARY KEY NOT NULL, name TEXT, mana_cost TEXT, type_line TEXT, oracle_text TEXT, power TEXT, toughness TEXT, artist TEXT, rarity TEXT, setCode TEXT, flavor_text TEXT, prints_search_uri TEXT, expansionName TEXT, collectorNumber TEXT)");
            database.execSQL("INSERT INTO Card_temp (id, name, mana_cost, type_line, oracle_text, power, toughness, artist, rarity, setCode, flavor_text, prints_search_uri, expansionName, collectorNumber) SELECT id, name, mana_cost, type_line, oracle_text, power, toughness, artist, rarity, setCode, flavor_text, prints_search_uri, expansionName, collectorNumber FROM Card");
            database.execSQL("DROP TABLE Card");
            database.execSQL("ALTER TABLE Card_temp RENAME TO Card");
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "mtg_collector_database")
                            .addMigrations(MIGRATION_6_7) // Add the migration here
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}