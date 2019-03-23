package mergerobotics.memo.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mergerobotics.memo.R;
import mergerobotics.memo.dataobjects.Event;
import mergerobotics.memo.db.EventsDbAdapter;

import static mergerobotics.memo.db.EventsDbAdapter.EVENT_REF;

public class TeleopActivity extends AppCompatActivity {

    //For comment fields
    EditText commentText, commentTeamText;

    //For ease of sharing event data
    Event currentEvent;

    //timer variables
    private Handler m_handler;
    private Runnable m_handlerTask;
    private volatile boolean stopTimer;
    private int remainTime = 135;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_2019);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Extract the data passed from previous activity via the intent extras and update
        Intent thisIntent = getIntent(); // retrieve intent once
        currentEvent = (Event) thisIntent.getSerializableExtra(EVENT_REF);
        currentEvent.signature = Event.Phase.TELEOP.toString();
        currentEvent.phase = Event.Phase.TELEOP;

        // Default the team number in the comment area to this team number
        final TextView commentTeam = findViewById(R.id.teamNumber);
        commentTeam.setText(Integer.toString(currentEvent.teamNum));

        // Set up the countdown timer
        final TextView tvGameTime = (TextView) findViewById(R.id.timer_textView);
        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {

                if (remainTime == 0) {
                    tvGameTime.setText("Times Up");
                } else {
                    remainTime--;
                    int minutes = remainTime / 60;
                    int remainSec = remainTime - minutes * 60;
                    String remainSecString;
                    if (remainSec < 10)
                        remainSecString = "0" + remainSec;
                    else
                        remainSecString = remainSec + "";
// woo
                    tvGameTime.setText(minutes + ":" + remainSecString);

                    // set an alarm to run this again in 1 second
                    if (!stopTimer)
                        m_handler.postDelayed(m_handlerTask, 1000);  // 1 second delay
                }
            }
        };
        m_handlerTask.run();
    }

    public void endgamePage(View view){
        Intent intent = new Intent(this, EndGameActivity.class);

        // Pass on the event instance to the next activity
        currentEvent.phase = Event.Phase.ENDGAME;
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);

        // return to previous activity when endgame finishes
        finish();
    }

    //OnClick methods for popups
    public void cargoPickupPage(View view){
        Intent intent = new Intent(this, CargoPickupActivity.class);

        // Determine the game piece type from button label
        Button b = (Button)view;
        String buttonText = b.getText().toString();

        // Update event with game piece selected and phase
        currentEvent.eventType = buttonText;
        currentEvent.startTime = SystemClock.currentThreadTimeMillis();

        // Pass on the event instance to the next activity
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);
    }

    public void hatchPickupPage(View view){
        Intent intent = new Intent(this, HatchPickupActivity.class);

        // @TODO can change to gamepiecePickup method and use the Button text to determine next activity
        Button b = (Button)view;
        String buttonText = b.getText().toString();

        // Update event with game piece selected
        currentEvent.eventType = buttonText;
        currentEvent.startTime = SystemClock.currentThreadTimeMillis();

        // Pass on the event instance to the next activity
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);
    }

    public void goHandler(View view){

        // GO button has been pushed, get the user's comment data if any
        commentText = (EditText) findViewById(R.id.comment);
        String comments = commentText.getText().toString();

        // Create an event to store the comment if not empty, team part of the comment can be empty
        if (!comments.matches("")) {
            int cTeam = currentEvent.teamNum; // default to the team number being scouted

            commentTeamText = findViewById(R.id.teamNumber);
            String commentTeam = commentTeamText.getText().toString();

            if (!commentTeam.matches("")) {
                // GUI ensures that a number was entered
                cTeam = Integer.parseInt(commentTeam);
            }

            Event commentEvent = new Event(Event.Phase.TELEOP, Event.Cycle.COMMENT.toString(),
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

}
