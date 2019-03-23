package mergerobotics.memo.backend.DataObjects;

import android.util.Log;

import org.json.JSONObject;

public class Message {
	public static final String OK = "ok";
	public static final String VERSION_MISMATCH = "version_mismatch";
	public static final String UNKNOWN = "unknown";
	public static final String INVALID = "invalid";
	public static final String UNAUTHORIZED = "unauthorized";
	
	public static final String HANDSHAKE = "shake";
	public static final String PUSH = "push";
	public static final String PULL = "pull";
	public static final String DUMP_SEASON = "dump_season";
	public static final String DUMP_MATCHES = "dump_matches";
	public static final String REQUEST_MATCHES = "request_matches";
	
	public String type;
	public JSONObject data;
	
	public Message(String resp)
	{
		try {
			//Log.d("Network", "Received: '" +resp + "'");
			JSONObject o = new JSONObject(resp);
			this.type = o.getString("type");
			this.data = o;
		} catch (Exception e) {
			Log.e("Network", "Error while constructing response", e);
		}
	}
}
