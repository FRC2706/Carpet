package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mergerobotics.memo.R;
import mergerobotics.memo.dataobjects.Event;
import mergerobotics.memo.db.EventsDbAdapter;
import mergerobotics.memo.utilities.Utilities;

import static mergerobotics.memo.db.EventsDbAdapter.EVENT_REF;
import static mergerobotics.memo.utilities.Utilities.toastPlusLog;

public class StartLevelActivity extends AppCompatActivity {

    // Keep a copy of the data passed from the calling activity intent
    Event currentEvent;
    EventsDbAdapter eDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Extract the data passed from previous activity via the intent extras
        Intent thisIntent = getIntent(); // retrieve intent once
        currentEvent = (Event) thisIntent.getSerializableExtra(EVENT_REF);
        currentEvent.eventType = Event.Cycle.START.toString();
        setContentView(R.layout.activity_startlevel);

    }

    public void startLevelHandler(View view) {
    /*
        Each pickup button click will
        - determine the start level from the button text, save it in the event
        - provide a feedback message using Toast

        Upon button selection
        - update the database with the event
        - dismiss the window to return to calling page (once delivery done or cancelled)
        */

        // Get a reference to the database and open it
        eDB = new EventsDbAdapter(this);
        eDB.open();

        // Determine the level from button label
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        Utilities.toastPlusLog(this,
                currentEvent.eventType + " level " + buttonText);

        currentEvent.extra = buttonText; // we use the extra field for many things based on event type
        currentEvent.endTime = SystemClock.currentThreadTimeMillis();

        // Store the event in the database
        long id = eDB.insertData(currentEvent);
        if(id<=0)
        {
            // Failed to update the database
            toastPlusLog( this, buttonText + " Failed");
        } else
        {
            // Event saved to  the database
            toastPlusLog( this, buttonText + " saved " + Long.toString(id));
        }

        // Return to calling page
        finish();

    }
}
