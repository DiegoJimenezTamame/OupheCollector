package local.ouphecollector.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration1To2 extends Migration {
    public Migration1To2() {
        super(1, 2);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE card_collection_table ADD COLUMN acquisition_price REAL NOT NULL DEFAULT 0.0");
    }
}