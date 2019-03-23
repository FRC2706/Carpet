package mergerobotics.memo.backend;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;

import mergerobotics.memo.backend.DataObjects.*;

public class Database {
    public static SQLiteDatabase db;
    public static String path;

    public static void init(String path)
    {
        int op = SQLiteDatabase.CREATE_IF_NECESSARY;
        db = SQLiteDatabase.openDatabase(path, null, op);
        Database.path = path;
    }

    public static Team getTeam(int number)
    {
        Cursor c = db.rawQuery("select * from teams where team=?", new String[]{Integer.toString(number)});
        if (c.getCount() > 0) {
            c.moveToNext();
            return new Team(c.getInt(c.getColumnIndex("team")), c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("public_key")));
        }
        else return null;
    }

    public static Match getMatch(String competition, int match_number)
    {
        Cursor c = db.rawQuery("select * from matches where competition=? and number=?", new String[]{competition, Integer.toString(match_number)});
        if (c.getCount() > 0) {
            c.moveToNext();
            return new Match(
                    c.getInt(c.getColumnIndex("red1")),
                    c.getInt(c.getColumnIndex("red2")),
                    c.getInt(c.getColumnIndex("red3")),
                    c.getInt(c.getColumnIndex("blue1")),
                    c.getInt(c.getColumnIndex("blue2")),
                    c.getInt(c.getColumnIndex("blue3")),
                    c.getString(c.getColumnIndex("competition")),
                    c.getInt(c.getColumnIndex("number"))
            );
        }
        else return null;
    }

    public static Competition[] getCompetitions(int year)
    {
        // TODO: Finish this method
        return new Competition[0];
    }
    
    public static void insertCompetition(String competition, int year) {
    	if (competitionExists(competition)) return;
        ContentValues cv = new ContentValues();
        cv.put("competition", competition);
        cv.put("year", year);
        db.insert("competitions", null, cv);
        save();
        Log.d("Database", "Inserted competition (" + competition + ", " + year + ") into database");
    }
    
    public static void insertEvent(Event event) {
    	if (eventExists(event.signature)) return;
        ContentValues cv = new ContentValues();
        cv.put("type", event.type);
        cv.put("team_number", event.team_number);
        cv.put("competition", event.competition);
        cv.put("end_time", event.end_time);
        cv.put("extra",event.extra);
        cv.put("match_number", event.match);
        cv.put("scout_name", event.scout_name);
        cv.put("scout_team", event.scout_team);
        cv.put("signature", event.signature);
        cv.put("success", event.success);
        cv.put("start_time", event.start_time);
        db.insert("events", null, cv);
        save();
        Log.d("Database", "Inserted event into database!");
    }
    
    public static void insertEvents(Event[] events) {
        for (Event e : events) insertEvent(e);
    }
    
    public static void save() {
        db.close();
        int op = SQLiteDatabase.CREATE_IF_NECESSARY;
        db = SQLiteDatabase.openDatabase(path, null, op);
    }
    
    public static Event[] getEventsFrom(String competition, int team, int match) {
        Cursor c = db.rawQuery("select * from events where competition=? and team_number=? and match_number=?", new String[]{competition, String.valueOf(team), String.valueOf(match)});
        Event[] ret = new Event[c.getCount()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Event.fromSQLCursor(c);
            c.moveToNext();
        }
        return ret;
    }
    
    public static Event[] getEventsFrom(String competition) {
	    Cursor c = db.rawQuery("select * from events where competition=?", new String[]{competition});
	    Event[] ret = new Event[c.getCount()];
	    for (int i = 0; i < ret.length; i++) {
		    ret[i] = Event.fromSQLCursor(c);
		    c.moveToNext();
	    }
	    return ret;
    }
    
    public static boolean competitionExists(String competition){
        Cursor c = db.rawQuery("select * from competitions where competition=?", new String[]{competition});
        return c.getCount() > 0;
    }
    
    public static boolean eventExists(String signature) {
        Cursor c = db.rawQuery("select * from events where signature=?", new String[]{signature});
        return c.getCount() > 0;
    }
    
    public static boolean matchExists(String competition, int number) {
    	// Ignore syntax errors because someone (Cough Cough Alden) thought it was a good idea to use a reserved keyword as a column name.
	    @SuppressWarnings("SyntaxError") Cursor c = db.rawQuery("select * from matches where competition=? and match=?", new String[]{competition, String.valueOf(number)});
        return c.getCount() > 0;
    }
    
    public static void insertMatch(Match match) {
    	if (matchExists(match.competition, match.number)) return;
        ContentValues cv = new ContentValues();
        cv.put("red1", match.red1);
        cv.put("red2", match.red2);
        cv.put("red3", match.red3);
        cv.put("blue1", match.blue1);
        cv.put("blue2", match.blue2);
        cv.put("blue3", match.blue3);
        cv.put("competition", match.competition);
        cv.put("match", match.number);
        db.insert("matches", null, cv);
    }
    
    public static void insertMatches(Match[] matches) {
        for (Match m : matches) insertMatch(m);
    }
    
    public static Match[] getMatchesFor(String competition) {
    	Cursor c = db.rawQuery("select * from matches where competition=?", new String[]{competition});
    	Match[] ret = new Match[c.getCount()];
    	for (int i = 0; i < c.getCount(); i++) {
    		ret[i] = Match.fromSQLCursor(c);
    		c.moveToNext();
	    }
	    return ret;
    }
    
    public static Team[] getTeams() {
    	Cursor c = db.rawQuery("select * from teams", null);
    	Team[] ret = new Team[c.getCount()];
    	for (int i = 0; i < ret.length; i++) {
		    ret[i] = Team.fromSQLCursor(c);
		    c.moveToNext();
	    }
	    return ret;
    }
    
}