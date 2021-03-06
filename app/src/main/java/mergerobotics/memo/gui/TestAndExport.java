package mergerobotics.memo.gui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import mergerobotics.memo.R;
import mergerobotics.memo.backend.App;
import mergerobotics.memo.db.EventsDbAdapter;
import mergerobotics.memo.utilities.FileUtils;

import static android.provider.BaseColumns._ID;
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
import static mergerobotics.memo.utilities.Utilities.toastPlusLog;

public class TestAndExport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void writeCell(StringBuilder sb, String o) {
        sb.append(o);
        sb.append(",");
    }
    private void writeCell(StringBuilder sb, int o) {
        sb.append(o);
        sb.append(",");
    }
    private void writeCell(StringBuilder sb, double o) {
        sb.append(o);
        sb.append(",");
    }

    public String createEventsCsvString(Context context) {

        StringBuilder sb = new StringBuilder();
        // Add the header columns, in desired order
        writeCell(sb, _ID);
        writeCell(sb, COLUMN_NAME_SYNC_TIME);
        writeCell(sb, COLUMN_NAME_TEAM);
        writeCell(sb, COLUMN_NAME_MATCH);
        writeCell(sb, COLUMN_NAME_TYPE);
        writeCell(sb, COLUMN_NAME_EXTRA);
        writeCell(sb, COLUMN_NAME_SCOUT_NAME);
        writeCell(sb, COLUMN_NAME_SCOUT_TEAM);
        writeCell(sb, COLUMN_NAME_COMPETITION);
        writeCell(sb, COLUMN_NAME_SUCCESS + " cycle time"); // using success field for cycle time as well
        writeCell(sb, COLUMN_NAME_START_TIME);
        writeCell(sb, COLUMN_NAME_END_TIME);
        writeCell(sb, COLUMN_NAME_SIGNATURE);
        sb.append("\n");

        EventsDbAdapter eDB = new EventsDbAdapter(context);
        eDB.open();
        String eventsData = eDB.getDataInCsvString();

        sb.append(eventsData);

        return sb.toString();
    }

    public void dumpEventsData(View view) {
    /*
        This method dumps the full events table into a file

        */
        //saveEventsCsvFile(this, "allevents");
        double startTime = System.currentTimeMillis();
        String filename = "allevents.csv"; // could be added to gui, overwrites
        //String fileString = (String) createEventsCsvString(this);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED){

            // Create the file
            File file = new File(FileUtils.sLocalToplevelFilePath + "/" + filename); //filename

            try {
                (new File(file.getParent())).mkdirs();

                BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                String fileString = (String) createEventsCsvString(this);
                bw.write(fileString);

                bw.close();

                // Force the media scanner to scan this file so it shows up from a PC over USB.
                App.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            } catch (IOException e) {
                Log.d("Error saving csv", e.toString());
            }

            System.out.println("Total time: " + (System.currentTimeMillis() - startTime));
            toastPlusLog( this,"File written: " + FileUtils.sLocalToplevelFilePath + "/" + filename);

        }
        else
            toastPlusLog( this,"No file write permissions, check app permissions in Settings >> Apps >> MEMO ");

        // do not return automatically, need to look at the data
        //finish();

    }
}
