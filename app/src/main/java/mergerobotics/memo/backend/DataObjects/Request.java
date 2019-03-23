package mergerobotics.memo.backend.DataObjects;

import android.util.Log;

import org.json.JSONObject;

public class Request {

	
	public String type;
	public JSONObject data;
	
	public Request(String resp)
	{
		try {
			Log.d("Network", "Request data: " + resp);
			JSONObject o = new JSONObject(resp);
			this.type = o.getString("type");
			o.remove("type");
			this.data = o;
		} catch (Exception e) {
			Log.e("Network", "Error while constructing response", e);
		}
	}
}
