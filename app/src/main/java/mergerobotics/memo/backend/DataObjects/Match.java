package mergerobotics.memo.backend.DataObjects;

import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class Match {
    public int red1;
	public int red2;
	public int red3;
	public int blue1;
	public int blue2;
	public int blue3;
	public String competition;
	public int number;

    public static Match[] fromJSONArray(JSONArray matches) {
        Match[] ret = new Match[matches.length()];
        for (int i = 0; i < ret.length; i++) {
            try {
                JSONObject match = matches.getJSONObject(i);
                ret[i] = new Match(
                        match.getInt("red1"),
                        match.getInt("red2"),
                        match.getInt("red3"),
                        match.getInt("blue1"),
                        match.getInt("blue2"),
                        match.getInt("blue3"),
                        match.getString("competition"),
                        match.getInt("match")
            );
            } catch (Exception e) {
                Log.e("Match", "Error while converting JSON Array to Match Array", e);
                return null;
            }
        }
        return ret;
    }
    
    public static JSONArray toJSONArray(Match[] matches) {
    	JSONArray ret = new JSONArray();
    	for (Match m : matches) ret.put(toJSONObject(m));
    	return ret;
    }
    
    public static JSONObject toJSONObject(Match m) {
    	try {
		    JSONObject ret = new JSONObject();
		    ret.put("red1", m.red1);
		    ret.put("red2", m.red2);
		    ret.put("red3", m.red3);
		    ret.put("blue1", m.blue1);
		    ret.put("blue2", m.blue2);
		    ret.put("blue3", m.blue3);
		    ret.put("competition", m.competition);
		    ret.put("match", m.number);
		    return ret;
    	} catch (Exception e) {
    		Log.e("Match", "Error while converting Match to JSONObject", e);
    		return null;
	    }
    }
    
    public static Match fromSQLCursor(Cursor c) {
    	return new Match (
    			c.getInt(c.getColumnIndex("red1")),
			    c.getInt(c.getColumnIndex("red2")),
			    c.getInt(c.getColumnIndex("red3")),
			    c.getInt(c.getColumnIndex("blue1")),
			    c.getInt(c.getColumnIndex("blue2")),
			    c.getInt(c.getColumnIndex("blue3")),
			    c.getString(c.getColumnIndex("competition")),
			    c.getInt(c.getColumnIndex("match"))
	    );
    }
    
    public Match(int red1, int red2, int red3, int blue1, int blue2, int blue3, String competition, int number)
    {
        this.red1 = red1;
        this.red2 = red2;
        this.red3 = red3;
        this.blue1 = blue1;
        this.blue2 = blue2;
        this.blue3 = blue3;
        this.competition = competition;
        this.number = number;
    }
}
