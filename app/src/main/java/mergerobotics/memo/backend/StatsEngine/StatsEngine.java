package mergerobotics.memo.backend.StatsEngine;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class StatsEngine extends SQLiteOpenHelper{
    //
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "merg.db";

    public StatsEngine(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

