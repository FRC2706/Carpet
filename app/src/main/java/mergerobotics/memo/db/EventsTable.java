package mergerobotics.memo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import mergerobotics.memo.dataobjects.Event;

import static mergerobotics.memo.db.EventsContract.EventsEntry.*;

public class EventsTable {
    private EventsHelper mEventsHelper;
    public EventsTable(Context context)
    {
        mEventsHelper = new EventsHelper(context);
    }

    public long insertData(Event event, String signature)
    {
        SQLiteDatabase dbEvents = mEventsHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TEAM_NUMBER, Integer.toString(event.teamNum));
        contentValues.put(COLUMN_NAME_COMPETITION, Integer.toString(event.competition));
        contentValues.put(COLUMN_NAME_SUCCESS, event.success);
        contentValues.put(COLUMN_NAME_START_TIME, event.startTime);
        contentValues.put(COLUMN_NAME_END_TIME, event.endTime);
        contentValues.put(COLUMN_NAME_EXTRA, event.extra);
        contentValues.put(COLUMN_NAME_SCOUT_NAME , event.scoutName);
        contentValues.put(COLUMN_NAME_SCOUT_TEAM, Integer.toString(event.scoutTeam));
        contentValues.put(COLUMN_NAME_SIGNATURE, signature);
        return dbEvents.insert(TABLE_NAME, null , contentValues);
    }

    public String getData()
    {
        SQLiteDatabase db = mEventsHelper.getWritableDatabase();
        String[] columns = {COLUMN_NAME_UID,COLUMN_NAME_TYPE,COLUMN_NAME_TEAM_NUMBER,COLUMN_NAME_TEAM_NUMBER,
                COLUMN_NAME_COMPETITION,COLUMN_NAME_SUCCESS,COLUMN_NAME_START_TIME,COLUMN_NAME_END_TIME,
                COLUMN_NAME_EXTRA,COLUMN_NAME_SCOUT_NAME, COLUMN_NAME_SCOUT_TEAM, COLUMN_NAME_SIGNATURE };
        Cursor cursor =db.query(TABLE_NAME, columns, "*",null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid= cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_UID));
            double timestamp= cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_SYNC_TIME));
            String eventType= cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TYPE));
            int competition= cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_COMPETITION));
            int success= cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SUCCESS));
            double startTime= cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_START_TIME));
            double endTime= cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_END_TIME));
            String extra= cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EXTRA));
            String scoutName= cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SCOUT_NAME));
            String scoutTeam= cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SCOUT_TEAM));

            // add rest of columns later
            buffer.append(cid+ " " +
                    Double.toString(timestamp) + " " +
                    eventType + " " +
                    Integer.toString(competition) + " " +
                    Integer.toString(success) + " " +
                    Double.toString(startTime) + " " +
                    Double.toString(endTime) + " " +
                    extra + " " +
                    scoutName + " " +
                    scoutTeam +
                    " \n");
        }

        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = mEventsHelper.getWritableDatabase();
        String[] whereArgs ={uname};

        Log.i(getClass().getName(), uname + " delete not ready yet");
        int count =db.delete(mEventsHelper.TABLE_NAME ,COLUMN_NAME_UID +" = ?",whereArgs);
        return  count;
    }

    public void updateName(String oldName , String newName)
    {
        /// ***** NOT IMPLEMENTED for our app
//        SQLiteDatabase db = mEventsHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(mEventsHelper.NAME,newName);
//        String[] whereArgs= {oldName};
//        int count =db.update(mEventsHelper.TABLE_NAME,contentValues, mEventsHelper.NAME+" = ?",whereArgs );
//        return count;
        Log.i(getClass().getName(), "updateName not implemented");

    }

}

