package mergerobotics.memo.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

        // Default the team number in the comment area to this team number
        TextView commentTeam = findViewById(R.id.teamNumber);
        commentTeam.setText(Integer.toString(currentEvent.teamNum));
    }

    public void deliveryHandler(View view) {
    /*
        This is a generic handler for processing delivery outcomes.

        Each button click will
        - provide a feedback view message
        - reset end time in the event data upon delivery outcome button selection
        - calculate a full cycle time
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

        currentEvent.extra = buttonText; // extra field is context sensitive based on type
        currentEvent.endTime = SystemClock.currentThreadTimeMillis();

        // Calculate the full cycle time based on delivery button click
        currentEvent.success = (int) (currentEvent.endTime - currentEvent.startTime);

        toastPlusLog( this, "Full cycle time in ms: " + currentEvent.success);

        // Store the delivery event in the database
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

        finish();
    }

    public void goHandler(View view){
        EditText commentText, commentTeamText;

        // GO button has been pushed, get the user's comment data if any
        commentText = findViewById(R.id.comment);
        String comments = commentText.getText().toString();

        // Create an event to store the comment if not empty, team part of the comment will default
        if (!comments.matches("")) {
            int cTeam = currentEvent.teamNum; // default to the team number being scouted

            commentTeamText = findViewById(R.id.teamNumber);
            String commentTeam = commentTeamText.getText().toString();

            if (!commentTeam.matches("")) {
                // GUI ensures that a number was entered
                cTeam = Integer.parseInt(commentTeam);
            }

            Event commentEvent = new Event(Event.Phase.AUTONOMOUS, Event.Cycle.COMMENT.toString(),
                    cTeam, currentEvent.match, comments, currentEvent.scoutName, currentEvent.scoutTeam);
            commentEvent.startTime = SystemClock.currentThreadTimeMillis();

            // Save the event in the database
            EventsDbAdapter eDB = new EventsDbAdapter(this);
            eDB.open();

            // Insert the event
            long id = eDB.insertData(commentEvent);

            // Clear the comment field after updating db
            commentText.setText("");

            // Ensure the keyboard is gone (will be left up if user did not click Done)
            // The try block is required in case the user did click Done
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch(Exception ignored) {
            }
        }
        commentText.clearFocus();
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

        finish();

    }
}
