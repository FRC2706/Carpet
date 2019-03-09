package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import mergerobotics.memo.R;
import mergerobotics.memo.dataobjects.Event;
import mergerobotics.memo.db.EventsDbAdapter;

import static mergerobotics.memo.db.EventsDbAdapter.EVENT_REF;
import static mergerobotics.memo.utilities.Utilities.toastPlusLog;

public class sandstormActivity extends AppCompatActivity {
    // definitions for data sharing btw activities (via putExtra)
    private static final String SCOUT_NAME = "scout";
    private static final String SCOUT_TEAM = "scoutTeam";
    private static final String TEAM = "team";
    private static final String MATCH = "match";

    // This data needs to be saved for db event
    // NB: scoutTeam not currently in GUI user input
    private String scoutName;
    private int teamNum, matchNum, scoutTeam;

    //timer variables
    private Handler m_handler;
    private Runnable m_handlerTask;
    private volatile boolean stopTimer;
    private int remainTime = 15;

    EditText commentText, commentTeamText;

    // Create an event to populate user input and share with subsequent activities
    Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandstorm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Extract the data passed from Prematch via the intent extras
        Intent thisIntent = getIntent(); // retrieve intent once
        teamNum = thisIntent.getIntExtra(TEAM, 0);
        scoutName = thisIntent.getStringExtra(SCOUT_NAME);
        scoutTeam = thisIntent.getIntExtra(SCOUT_TEAM, 2706);
        matchNum = thisIntent.getIntExtra(MATCH, 0);

        currentEvent = new Event(Event.Phase.AUTONOMOUS, "NIL",
                teamNum, matchNum,
                " ", scoutName, scoutTeam);


        final TextView tvGameTime = (TextView) findViewById(R.id.autoTimer);
        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {

                if (remainTime == 0) {
                    tvGameTime.setText("Times Up");
                } else {
                    remainTime--;
                    int minuets = remainTime / 60;
                    int remainSec = remainTime - minuets * 60;
                    String remainSecString;
                    if (remainSec < 10)
                        remainSecString = "0" + remainSec;
                    else
                        remainSecString = remainSec + "";
// woo
                    tvGameTime.setText(minuets + ":" + remainSecString);

                    // set an alarm to run this again in 1 second
                    if (!stopTimer)
                        m_handler.postDelayed(m_handlerTask, 1000);  // 1 second delay
                }
            }
        };
        m_handlerTask.run();
    }

    public void startLevel (View view){
        Intent startLevelIntent = new Intent(this, StartLevelActivity.class);

        // Pass on the event instance to the teleop activity
        startLevelIntent.putExtra(EVENT_REF, currentEvent);
        startActivity(startLevelIntent);
    }

    public void onCheckboxClicked (View view){
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Save the control or habline input from autonomous phase
        CheckBox c = (CheckBox)view;
        String checkboxText = c.getText().toString();

        // Note that unchecking does NOT remove the data from the database, duplicates can be ignored
        if (checked) {
            // get a reference to the events table
            EventsDbAdapter eDB = new EventsDbAdapter(this);
            eDB.open();

            // Save the user checkbox data in an event
            Event newEvent = new Event(Event.Phase.AUTONOMOUS, Event.Cycle.START.toString(), teamNum, matchNum,
                checkboxText, scoutName, scoutTeam);

            // Insert the event
            long id = eDB.insertData(newEvent);
            if(id<=0)
            {
                // Failed to update the database
                toastPlusLog( this, checkboxText + " Failed");
            } else
            {
                // Event saved to  the database
                toastPlusLog( this, checkboxText + " Saved " + Long.toString(id));
            }
        }

    }

    public void teleopPage(View view){
        Intent teleopIntent = new Intent(this, teleopActivity2019.class);

        // Pass on the event data to the teleop activity
        teleopIntent.putExtra(EVENT_REF, currentEvent);
        startActivity(teleopIntent);
    }

    //OnClick methods for cargo, hatch and delivery buttons
    public void cargoPickupPage(View view){
        Intent intent = new Intent(this, CargoPickupActivity.class);
        // Determine the game piece type from button label
        Button b = (Button)view;
        String buttonText = b.getText().toString();

        // Update event with game piece input based on button selection
        currentEvent.eventType = buttonText;
        currentEvent.startTime = SystemClock.currentThreadTimeMillis();

        // Pass on the event instance to the next activity
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);
    }
    public void hatchPickupPage(View view){
        Intent intent = new Intent(this, HatchPickupActivity.class);
        // Determine the game piece type from button label
        Button b = (Button)view;
        String buttonText = b.getText().toString();

        // Update event with game piece input based on button selection
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
            int cTeam = teamNum; // default to the team number being scouted

            commentTeamText = findViewById(R.id.teamNumber);
            String commentTeam = commentTeamText.getText().toString();

            if (!commentTeam.matches("")) {
                // GUI ensures that a number was entered
                cTeam = Integer.parseInt(commentTeam);
            }

            Event commentEvent = new Event(Event.Phase.AUTONOMOUS, Event.Cycle.COMMENT.toString(),
                    cTeam, matchNum, comments, scoutName, scoutTeam);
            commentEvent.startTime = SystemClock.currentThreadTimeMillis();

            // Save the event in the database
            EventsDbAdapter eDB = new EventsDbAdapter(this);
            eDB.open();

            // Insert the event
            long id = eDB.insertData(commentEvent);

            // Could provide a better failure message later if desired, keeping as is for now as the
            // id will give the key number of the entry in the table providing a warm fuzzy on how
            // many entries are in the db
            toastPlusLog(this, "Comment event write result " + Long.toString(id));
        }
        commentText.clearFocus();
    }

}
