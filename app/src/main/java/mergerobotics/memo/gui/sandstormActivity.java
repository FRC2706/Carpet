package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mergerobotics.memo.R;
import mergerobotics.memo.dataobjects.Event;
import mergerobotics.memo.db.EventsHelper;
import mergerobotics.memo.db.EventsTable;

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

    // Keep track of when sandstorm created, this will be the timestamp for the start cycle
    private double startTime;

    // Save the data provided by the user on the Sandstorm page
    private String startLevel = "1";
    private String control = "Driver";
    private boolean crossHabline = true;
    private String sandstormComments = "jpl";

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

        // initialize the start cycle start time
        startTime = SystemClock.currentThreadTimeMillis();
    }

    public void teleopPage(View view){
        Intent teleopIntent = new Intent(this, teleopActivity2019.class);
        EventsTable eTable = new EventsTable(this);
        EventsHelper eHelper = new EventsHelper(this);

        // @TODO Need to pass the start level and control input from user, hardcode for now and store in comments
        String tempStartData = new String (scoutName + " team: " + Integer.toString(teamNum) +
                " Start lvl 1 Driver CrossHabline"
                 );

        // Save the non-cycle event data in the database before proceeding to Teleop
        // echo values before proceeding
        toastPlusLog( this, tempStartData);

        Event startingEvent = new Event(Event.Phase.SAND, Event.Cycle.START, teamNum, matchNum, "FIRST",
                1, startTime, SystemClock.currentThreadTimeMillis(),
                " Start lvl 1 Driver CrossHabline", scoutName, scoutTeam);

        // Insert the event
        long id = eTable.insertData(startingEvent, "jpl");
        if(id<=0)
        {
            // Failed to update the database
            toastPlusLog( this, tempStartData + " Failed");
        } else
        {
            // Event saved to  the database
            toastPlusLog( this, tempStartData + " Saved");
        }

        // Pass on the user input to next activity TODO

        startActivity(teleopIntent);
    }

    //OnClick methods for cargo, hatch and delivery buttons
    public void cargoPickupPage(View view){
        Intent intent = new Intent(this, CargoPickupActivity.class);
        startActivity(intent);
    }
    public void hatchPickupPage(View view){
        Intent intent = new Intent(this, HatchPickupActivity.class);
        startActivity(intent);
    }

    public void deliveryCyclePage(View view){
        Intent intent = new Intent(this, DeliveryCycleActivity.class);
        startActivity(intent);
    }

}
