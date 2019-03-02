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

public class DeliveryCycleActivity extends AppCompatActivity {

    // Keep a copy of the data passed from the calling activity intent
    Event currentEvent;
    EventsDbAdapter eDB;
    Intent thisIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleopdelivery);

        // Extract the data passed from previous activity via the intent extras
        thisIntent = getIntent(); // retrieve intent once
        currentEvent = (Event) thisIntent.getSerializableExtra(EVENT_REF);
    }

    public void deliveryHandler(View view) {
    /*
        This is a generic handler for processing delivery outcomes.

        Each button click will
        - provide a feedback view message
        - reset end time in the event data upon delivery outcome button selection
        - calculate a full cycle time (not saved in db at this time)
        - write to the events database (unless cancelled)
        - dismiss the window to return to previous page
        */

        // Get a reference to the database and open it
        eDB = new EventsDbAdapter(this);
        eDB.open();

        // Determine the delivery location from the button label
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        Utilities.toastPlusLog(this,
                currentEvent.eventType + " delivery location " + buttonText);

        currentEvent.extra = currentEvent.extra + " " + buttonText; //for now
        currentEvent.location = buttonText;
        currentEvent.endTime = SystemClock.currentThreadTimeMillis();

        // Store the pickup event in the database
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

        // Calculate the full cycle time based on delivery button click
        double cycleTime = currentEvent.endTime - currentEvent.startTime;
        toastPlusLog( this, "Full cycle time in ms: " + Double.toString(cycleTime));

        finish();
    }


    // The following methods are no longer needed (should use delivery handler), will remove later
    public void deliverCargoToRocketLvl1 (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database
        - dismiss the window to return to previous page
        */
        Toast myToast = Toast.makeText(this, "Delivery to Rocket Ship lvl 1",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Delivery to Rocket Ship lvl 1");

        // Return to previous page, do we want a slight delay ?
        finish();
    }


    public void deliverCargoToRocketLvl2 (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database
        - dismiss the window to return to previous page
        */
        Toast myToast = Toast.makeText(this, "Delivery to Rocket Ship lvl 2",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Delivery to Rocket Ship lvl 2");

        // Return to previous page, do we want a slight delay ?
        finish();
    }

    public void deliverCargoToRocketLvl3 (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database
        - dismiss the window to return to previous page
        */
        Toast myToast = Toast.makeText(this, "Delivery to Rocket Ship lvl 3",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Delivery to Rocket Ship lvl 3");

        // Return to previous page
        finish();
    }

    public void deliveryDropped (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database
        - dismiss the window to return to previous page
        */
        Toast myToast = Toast.makeText(this, "Delivery dropped",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Delivery dropped");

        // Return to previous page, do we want a slight delay ?
        finish();
    }

    public void cancelled (View view) {
    /*
        This is a generic method to use when a scout clicks a cancel button in any of our
        nested activities, similar to back Note: needs to be in context, hence repeated per
        Activity for now
        - provide a feedback view message
        - dismiss the window to return to previous page
        */

        Toast myToast = Toast.makeText(this, "Cancelled",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cancelled"); // remove later

        finish();

    }
}
