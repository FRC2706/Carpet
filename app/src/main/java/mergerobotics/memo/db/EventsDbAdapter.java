package mergerobotics.memo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import mergerobotics.memo.dataobjects.Event;

import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_COMPETITION;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_END_TIME;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_EXTRA;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_MATCH;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SCOUT_NAME;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SCOUT_TEAM;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SIGNATURE;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_START_TIME;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SUCCESS;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SYNC_TIME;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_TEAM;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_TYPE;
import static mergerobotics.memo.db.EventsContract.EventsEntry.TABLE_NAME;
import static mergerobotics.memo.db.EventsContract.EventsEntry._ID;

public class EventsDbAdapter {

    private EventsHelper mEventsHelper;
    private SQLiteDatabase mEventsDb;
    private final Context mCtx; // this is the context associated with db
    public static final String EVENT_REF = "event_ref";

    public EventsDbAdapter(Context context)
    {

        // deferred until open method, mEventsHelper = new EventsHelper(context);
        mCtx = context;
        //Note that the database is created later on first read or write, via onCreate
        // Also note that the onCreate will NOT be called if the database already exists
        // on the system. To force a recreate, uninstall the app from the phone manually
    }

    public long insertData(Event event )
    {
        SQLiteDatabase dbEvents = mEventsHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_SYNC_TIME, event.startTime);
        contentValues.put(COLUMN_NAME_TYPE, event.eventType);
        contentValues.put(COLUMN_NAME_TEAM, Integer.toString(event.teamNum));
        contentValues.put(COLUMN_NAME_MATCH, event.match);
        contentValues.put(COLUMN_NAME_COMPETITION, event.competition);
        contentValues.put(COLUMN_NAME_SUCCESS, event.success);
        contentValues.put(COLUMN_NAME_START_TIME, event.startTime);
        contentValues.put(COLUMN_NAME_END_TIME, event.endTime);
        contentValues.put(COLUMN_NAME_EXTRA, event.extra);
        contentValues.put(COLUMN_NAME_SCOUT_NAME , event.scoutName);
        contentValues.put(COLUMN_NAME_SCOUT_TEAM, Integer.toString(event.scoutTeam));
        contentValues.put(COLUMN_NAME_SIGNATURE, event.signature);
        return dbEvents.insert(TABLE_NAME, null , contentValues);
    }

    public String getData()
    {
        SQLiteDatabase db = mEventsHelper.getReadableDatabase();
        String[] columns = {_ID,COLUMN_NAME_SYNC_TIME,COLUMN_NAME_TYPE, COLUMN_NAME_TEAM, COLUMN_NAME_MATCH,
                COLUMN_NAME_COMPETITION,COLUMN_NAME_SUCCESS,COLUMN_NAME_START_TIME,COLUMN_NAME_END_TIME,
                COLUMN_NAME_EXTRA,COLUMN_NAME_SCOUT_NAME, COLUMN_NAME_SCOUT_TEAM, COLUMN_NAME_SIGNATURE };

//        Cursor cursor = db.query(
//                TABLE_NAME,             // The table to query
//                projection,             // The array of columns to return (pass null to get all)
//                selection,              // The columns for the WHERE clause
//                selectionArgs,          // The values for the WHERE clause
//                null,                   // don't group the rows
//                null,                   // don't filter by row groups
//                sortOrder               // The sort order

        Cursor cursor =db.query(TABLE_NAME, columns, null,null,null,
                null,null,null);

        if (cursor != null) {
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                int cid = cursor.getInt(cursor.getColumnIndex(_ID));
                double timestamp = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_SYNC_TIME));
                String eventType = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TYPE));
                int team = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TEAM));
                int match = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MATCH));
                String competition = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMPETITION));
                int success = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SUCCESS));
                double startTime = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_START_TIME));
                double endTime = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_END_TIME));
                String extra = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EXTRA));
                String scoutName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SCOUT_NAME));
                String scoutTeam = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SCOUT_TEAM));
                String signature = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SIGNATURE));

                // add rest of columns later
                buffer.append(cid + " " +
                        Double.toString(timestamp) + " " +
                        eventType + " " +
                        Integer.toString(team) + " " +
                        Integer.toString(match) + " " +
                        competition + " " +
                        Integer.toString(success) + " " +
                        Double.toString(startTime) + " " +
                        Double.toString(endTime) + " " +
                        extra + " " +
                        scoutName + " " +
                        scoutTeam +
                        signature +
                        " \n");
            }

            return buffer.toString();
        }
        else return "Null cursor";
    }

    public String getDataInCsvString()
    {
        SQLiteDatabase db = mEventsHelper.getReadableDatabase();
        String[] columns = {_ID,COLUMN_NAME_SYNC_TIME,COLUMN_NAME_TYPE, COLUMN_NAME_TEAM, COLUMN_NAME_MATCH,
                COLUMN_NAME_COMPETITION,COLUMN_NAME_SUCCESS,COLUMN_NAME_START_TIME,COLUMN_NAME_END_TIME,
                COLUMN_NAME_EXTRA,COLUMN_NAME_SCOUT_NAME, COLUMN_NAME_SCOUT_TEAM, COLUMN_NAME_SIGNATURE };

//        Explanation Cursor cursor = db.query(
//                TABLE_NAME,             // The table to query
//                projection,             // The array of columns to return (pass null to get all)
//                selection,              // The columns for the WHERE clause
//                selectionArgs,          // The values for the WHERE clause
//                null,                   // don't group the rows
//                null,                   // don't filter by row groups
//                sortOrder               // The sort order

        Cursor cursor =db.query(TABLE_NAME, columns, null,null,null,
                null,null,null);
        Event currentEvent = new Event();

        if (cursor != null) {
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                currentEvent._id = cursor.getInt(cursor.getColumnIndex(_ID));
                currentEvent.timestamp = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_SYNC_TIME));
                currentEvent.eventType = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TYPE));
                currentEvent.teamNum = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TEAM));
                currentEvent.match = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MATCH));
                currentEvent.competition = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMPETITION));
                currentEvent.success = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SUCCESS));
                currentEvent.startTime = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_START_TIME));
                currentEvent.endTime = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_END_TIME));
                currentEvent.extra = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EXTRA));
                currentEvent.scoutName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SCOUT_NAME));
                currentEvent.scoutTeam = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SCOUT_TEAM));
                currentEvent.signature = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SIGNATURE));

                // add rest of columns later
                buffer.append(currentEvent._id + ", " +
                        Double.toString(currentEvent.timestamp) + ", " +
                        Integer.toString(currentEvent.teamNum) + ", " +
                        Integer.toString(currentEvent.match) + ", " +
                        currentEvent.eventType + ", " +
                        currentEvent.extra + ", " +
                        currentEvent.scoutName + ", " +
                        currentEvent.scoutTeam + ", " +
                        currentEvent.competition + ", " +
                        Integer.toString(currentEvent.success) + ", " +
                        Double.toString(currentEvent.startTime) + ", " +
                        Double.toString(currentEvent.endTime) + ", " +
                        currentEvent.signature + ", " +
                        " \n");
            }

            cursor.close();
            return buffer.toString();
        }
        else return "Null cursor";
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = mEventsHelper.getWritableDatabase();
        String[] whereArgs ={uname};

        Log.i(getClass().getName(), uname + " delete not ready yet");
        return db.delete(TABLE_NAME ,
                EventsContract.EventsEntry._ID +" = ?",whereArgs);
    }


    /**
     * This method will open the database and creates it if necessary (handled
     * by getWritableDatabase method)
     *
     * <p>
     * @return EventsDbAdapter instance is returned to provide access to the data
     * </p><p>
     * An exception is thrown if there are any issues
     * </p>

     */
    public EventsDbAdapter open() throws android.database.SQLException{

        mEventsHelper = new EventsHelper(mCtx);
        mEventsDb = mEventsHelper.getWritableDatabase();
        return this;
    }

    /**
     * This method will close the database
     * <p>
     */
    public void close() {

        mEventsHelper.close();
    }

    public void updateEvent(String oldName , String newName)
    {
        /// ***** NOT IMPLEMENTED for our app
//        SQLiteDatabase db = mEventsHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(mEventsHelper.NAME,newName);
//        String[] whereArgs= {oldName};
//        int count =db.update(mEventsHelper.TABLE_NAME,contentValues, mEventsHelper.NAME+" = ?",whereArgs );
//        return count;
        Log.i(getClass().getName(), "updateEvent not implemented");

    }

    public void onDeleteRequest() {
        SQLiteDatabase db = mEventsHelper.getWritableDatabase();
        String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        Log.i(getClass().getName(), "deleting " + TABLE_NAME );
        db.execSQL(DROP_TABLE);
    }
}

