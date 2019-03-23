package mergerobotics.memo.backend.DataObjects;

import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class Event {
	public static Event[] fromJSONArray(JSONArray json) {
		Event[] ret = new Event[json.length()];
		try {
			for (int i = 0; i < json.length(); i++) {
				JSONObject obj = json.getJSONObject(i);
				String type = obj.getString("type");
				int team_number = obj.getInt("team_number");
				int match = obj.getInt("match_number");
				String competition = obj.getString("competition");
				int success = obj.getInt("success");
				int start_time = obj.getInt("start_time");
				int end_time = obj.getInt("end_time");
				String extra = obj.getString("extra");
				String scout_name = obj.getString("scout_name");
				int scout_team = obj.getInt("scout_team");
				String signature = obj.getString("signature");
				ret[i] = new Event(type, team_number, match, competition, success, start_time, end_time, extra, scout_name, scout_team, signature);
			}
		} catch (Exception e) {
			Log.e("Event", "Error while converting JSON Array to event array", e);
		}
		return ret;
	}
	
	public static JSONObject toJSONObject(Event event) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("type", event.type);
			obj.put("team_number", event.team_number);
			obj.put("match_number", event.match);
			obj.put("competition", event.competition);
			obj.put("success", event.success);
			obj.put("start_time", event.start_time);
			obj.put("end_time", event.end_time);
			obj.put("extra", event.extra);
			obj.put("scout_name", event.scout_name);
			obj.put("scout_team", event.scout_team);
			obj.put("signature", event.signature);
			return obj;
		} catch (Exception e) {
			Log.e("Event", "Error while converting Event to JSONObject", e);
		}
		return null;
	}
	
	public static JSONArray toJSONArray(Event[] events){
		JSONArray ret = new JSONArray();
		for (Event e : events)
			ret.put(Event.toJSONObject(e));
		return ret;
	}
	
	public static Event fromSQLCursor(Cursor c) {
		return new Event(
				c.getString(c.getColumnIndex("type")),
				c.getInt(c.getColumnIndex("team_number")),
				c.getInt(c.getColumnIndex("match")),
				c.getString(c.getColumnIndex("competition")),
				c.getInt(c.getColumnIndex("success")),
				c.getInt(c.getColumnIndex("start_time")),
				c.getInt(c.getColumnIndex("end_time")),
				c.getString(c.getColumnIndex("extra")),
				c.getString(c.getColumnIndex("scout_name")),
				c.getInt(c.getColumnIndex("scout_team")),
				c.getString(c.getColumnIndex("signature"))
		);
	}
	
	public String type;
	public int team_number;
	public int match;
	public String competition;
	public int success;
	public int start_time;
	public int end_time;
	public String extra;
	public String scout_name;
	public int scout_team;
	public String signature;
	
	
	public Event(String type, int team_number, int match, String competition, int success, int start_time, int end_time, String extra, String scout_name, int scout_team, String signature) {
		this.type = type;
		this.team_number = team_number;
		this.match = match;
		this.competition = competition;
		this.success = success;
		this.start_time = start_time;
		this.end_time = end_time;
		this.extra = extra;
		this.scout_name = scout_name;
		this.scout_team = scout_team;
		this.signature = signature;
	}
}
