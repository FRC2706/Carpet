package mergerobotics.memo.backend;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import mergerobotics.memo.R;
import mergerobotics.memo.backend.App;
import mergerobotics.memo.backend.DataObjects.MatchSchedule;
import mergerobotics.memo.backend.interfaces.DataRequester;



public class BlueAllianceUtils {
    private static final String BASE_URL = "http://www.thebluealliance.com/api/v3/";
    private static final String AUTH_KEY = "8GLetjJXz2pNCZuY0NnwejAw0ULn9TzbsYeLkYyzeKwDeRsK9MiDnxEGgy6UksW1";


    private Activity mActivity;

    private static boolean sPermissionsChecked = false;

    public static boolean isConnected(Activity activity) {
        if (activity == null)
            return false;

        // Check if they have enabled the permissions

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    // Gets the list of all teams attending the event
    public static void fetchTeamsRegisteredAtEvent(final DataRequester requester) {
        // Check if device is connected to the internet
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null)
            return;

        new Thread() {
            public void run() {
                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(App.getContext());
                String TBA_Event = SP.getString(App.getContext().getResources().getString(R.string.PROPERTY_event), "<Not Set>");

                Request request = new Request.Builder()
                        .url(BASE_URL + "event/" + TBA_Event + "/teams/keys")
                        .header("X-TBA-Auth-Key", AUTH_KEY)
                        .build();
                MatchSchedule schedule;

//                try {
//                    Response response = WebServerUtils.client.newCall(request).execute();
//
//                    schedule = new MatchSchedule();
//                    schedule.addToListOfTeamsAtEvent(response.body().string());
//
//                    response.close();
//                } catch (IOException e) {
//                    Log.d("Error getting teams: ", e.toString());
//                    return;
//                }

//                requester.updateMatchSchedule(schedule);
            }
        }.start();
    }
}
