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

public class CargoPickupActivity extends AppCompatActivity {

    // Keep a copy of the data passed from the calling activity intent
    Event currentEvent;
    EventsDbAdapter eDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Extract the data passed from previous activity via the intent extras
        Intent thisIntent = getIntent(); // retrieve intent once
        currentEvent = (Event) thisIntent.getSerializableExtra(EVENT_REF);

        // Load the layout based on whether we are in Autonomous or Teleop phase
        if (currentEvent.phase == Event.Phase.TELEOP) {
            setContentView(R.layout.activity_cargopickupteleop);
        } else {
            setContentView(R.layout.activity_cargopickup);
        }

    }

    public void cargoPickupHandler(View view) {
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

        // Determine the game piece type from button label
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        Utilities.toastPlusLog(this,
                currentEvent.eventType + " pickup " + buttonText);

        currentEvent.extra = buttonText; // we use the extra field for many things based on event type
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

        // pass the event data to the delivery cycle
        Intent intent = new Intent(this, DeliveryCycleActivity.class);
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);

        // Return to calling page after the delivery cycle activity has been completed
        finish();

    }

    public void cancelled (View view) {
    /*
        This is a generic method to use when a scout clicks a cancel button in any of our
        nested activities, similar to back
        - provide a feedback view message
        - NO DATA is saved to the database
        - dismiss the window to return to Teleop page
        */

        Toast myToast = Toast.makeText(this, "Cancelled",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cancelled"); // remove later

        finish();

    }
}
