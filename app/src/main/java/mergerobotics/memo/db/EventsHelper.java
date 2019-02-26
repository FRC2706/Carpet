package mergerobotics.memo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static mergerobotics.memo.db.EventsContract.DATABASE_NAME;
import static mergerobotics.memo.db.EventsContract.DATABASE_VERSION;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SCOUT_NAME;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_UID;

public class EventsHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "events";
    private static final String EVENT_TAB = "CREATE TABLE TABLE_NAME ( " +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " sync_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, " +
            " type VARCHAR(10) NOT NULL, " +
            " team SMALLINT NOT NULL, " +
            " match_number TINYINT NOT NULL, " +
            " competition VARCHAR(16) NOT NULL, " +
            " success TINYINT NOT NULL, "+
            " start_time TINYINT  NOT NULL, "+
            " end_time TINYINT  NOT NULL, "+
            " extra VARCHAR(64) NOT NULL, "+
            " scout_name VARCHAR(16) NOT NULL, "+
            " scout_team SMALLINT NOT NULL, "+
            " signature TEXT NOT NULL)";

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " ("+COLUMN_NAME_UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NAME_SCOUT_NAME+" VARCHAR(255) ,"+
            COLUMN_NAME_SCOUT_NAME+" VARCHAR(225));";

    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    private Context context;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the  database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public EventsHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public EventsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i(getClass().getName(), EVENT_TAB);
            db.execSQL(EVENT_TAB);
        } catch (Exception e) {
            Toast myToast = Toast.makeText(context, ""+e,
                    Toast.LENGTH_LONG);
            myToast.show();
        }

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // not needed at this time
        Toast myToast = Toast.makeText(context, "upgrade attempted :(",
                Toast.LENGTH_SHORT);
        myToast.show();
    }

}
