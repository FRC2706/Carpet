package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    }

    public void teleopPage(View view){
        Intent teleopIntent = new Intent(this, teleopActivity2019.class);

        // get our own reference to the table, should already exist at this point
        EventsDbAdapter eDB = new EventsDbAdapter(this);
        eDB.open();

        // @TODO Need to handle the start level and control input from user,
        // hardcode for now and store in comments. Remove this later.
        String tempStartData = new String (scoutName + " team: " + Integer.toString(teamNum) +
                " Start lvl 1 Driver CrossHabline"
                 );

        // echo values before proceeding
        toastPlusLog( this, tempStartData);

        // Save the non-cycle event data in the database before proceeding to Teleop
        Event startingEvent = new Event(Event.Phase.AUTONOMOUS, Event.Cycle.START.toString(), teamNum, matchNum,
                " Start lvl 1 Driver CrossHabline", scoutName, scoutTeam);

        // Insert the event
        long id = eDB.insertData(startingEvent);
        if(id<=0)
        {
            // Failed to update the database
            toastPlusLog( this, tempStartData + " Failed");
        } else
        {
            // Event saved to  the database
            toastPlusLog( this, tempStartData + " Saved " + Long.toString(id));
        }

        // Pass on the event instance to the teleop activity
        teleopIntent.putExtra(EVENT_REF, startingEvent);
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
