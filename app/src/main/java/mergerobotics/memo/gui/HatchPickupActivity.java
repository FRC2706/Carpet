package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mergerobotics.memo.R;
import mergerobotics.memo.dataobjects.Event;
import mergerobotics.memo.db.EventsDbAdapter;
import mergerobotics.memo.utilities.Utilities;

import static mergerobotics.memo.db.EventsDbAdapter.EVENT_REF;
import static mergerobotics.memo.utilities.Utilities.toastPlusLog;


public class HatchPickupActivity extends AppCompatActivity {

    // Keep a copy of the data passed from the calling activity intent
    Event currentEvent;
    EventsDbAdapter eDB;

    /* onCreate will:
        - draw the view according to the desired layout
        - extract any data passed in the intent
        - initialize variables for local processing*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Extract the data passed from previous activity via the intent extras
        Intent thisIntent = getIntent(); // retrieve intent once
        currentEvent = (Event) thisIntent.getSerializableExtra(EVENT_REF);

        // Load the layout based on whether we are in Autonomous or Teleop phase
        if (currentEvent.phase == Event.Phase.TELEOP) {
            setContentView(R.layout.activity_hatchpickupteleop);
        } else {
            setContentView(R.layout.activity_hatchpickup);
        }
    }

    /* Each activity needs to define it's onClick methods (or within it's hierarchy)
        in order to be able to see them in the list in the layout editor
    */
    public void hatchPickupHandler(View view) {
    /*
        Each pickup button click will
        - determine the pickup location from the button text, save it in the event
        - provide a feedback message using Toast

        Upon delivery button selection
        - update the end time, calculate a cycle time for echo (do we want that in db?)
        - update the database with the pickup event
        - pass on the event data to the delivery activity
        - launch the delivery cycle which will handle the delivery event (unless user cancels)

        Finally
        - dismiss the window to return to calling page (once delivery done or cancelled)
        */

        // Get a reference to the database and open it
        eDB = new EventsDbAdapter(this);
        eDB.open();

        // Determine the pickup location from the button label
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        Utilities.toastPlusLog(this,
                currentEvent.eventType + " pickup " + buttonText);

        currentEvent.extra = buttonText; // extra field is context sensitive based on event type
        currentEvent.endTime = SystemClock.currentThreadTimeMillis();

        // Calculate the pickup cycle time based on delivery button click
        double cycleTime = currentEvent.endTime - currentEvent.startTime;

        // stash the cycle time in signature string for now, signature is not implemented yet
        currentEvent.signature = Double.toString(cycleTime);

        // Store the pickup event in the database
        long id = eDB.insertData(currentEvent);

        // if id is <= 0 then db write failed, for now continue
        toastPlusLog(this, "Pickup " + buttonText +  " write result " + Long.toString(id));

        toastPlusLog( this, "Pickup cycle time in ms: " + currentEvent.signature);

        // pass the event data to the delivery cycle
        Intent intent = new Intent(this, DeliveryCycleActivity.class);
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);

        // Return to calling page after the delivery cycle has been completed
        finish();

    }


    public void cancelled (View view) {
    /*
        This is a generic method to use when a scout clicks a cancel button in any of our
        nested activities, similar to back
        - provide a feedback view message
        - NO DATA is saved in the database
        - dismiss the window to return to Teleop page
        */

        Toast myToast = Toast.makeText(this, "Cancelled",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cancelled"); // remove later

        finish();

    }
}
