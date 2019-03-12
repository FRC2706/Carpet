package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

import mergerobotics.memo.R;
import mergerobotics.memo.dataobjects.Event;
import mergerobotics.memo.db.EventsDbAdapter;

import static mergerobotics.memo.db.EventsDbAdapter.EVENT_REF;
import static mergerobotics.memo.utilities.Utilities.toastPlusLog;

public class EndGameActivity extends AppCompatActivity {

    // Save the endgame events during user input, they will only be saved in the db upon exit
    Event currentEvent, climbEvent;
    EventsDbAdapter eDB;

    // Slider input
    SeekBar climbTimeSeekbar, deadnessSeekbar, defenseSeekbar;
    int climbTime = 0;
    int deadness = 0;
    int defense = 0;

    // Textual input
    EditText commentText, commentTeamText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        // Extract the data passed from previous activity via the intent extras
        Intent thisIntent = getIntent(); // retrieve intent once
        currentEvent = (Event) thisIntent.getSerializableExtra(EVENT_REF);
        currentEvent.phase = Event.Phase.ENDGAME;
        currentEvent.signature = Event.Phase.ENDGAME.toString();

        // Set up handlers for climb time Seekbar
        climbTimeSeekbar =(SeekBar)findViewById(R.id.climbSeekBar);
        // perform seek bar change listener event used for getting the progress value
        climbTimeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                toastPlusLog(EndGameActivity.this, "Climb time value is :" + progressChangedValue);
                climbTime = progressChangedValue;
            }
        });

        // Set up handlers for Deadness Seekbar
        deadnessSeekbar =(SeekBar)findViewById(R.id.deadnessSeekBar);
        // perform seek bar change listener event used for getting the progress value
        deadnessSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                toastPlusLog(EndGameActivity.this, "Deadness value is :" + progressChangedValue);
                deadness = progressChangedValue;
            }
        });

        // Set up handlers for Defense Seekbar
        defenseSeekbar =(SeekBar)findViewById(R.id.defenceSeekbar);
        // perform seek bar change listener event used for getting the progress value
        defenseSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                toastPlusLog(EndGameActivity.this, "Defense value is :" + progressChangedValue);
                defense = progressChangedValue;
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Update the CLIMB event each time a level radio button is selected
        boolean checked = ((RadioButton) view).isChecked();
        climbEvent = currentEvent; // Initialize to same values, overwrite pertinent fields
        climbEvent.timestamp = SystemClock.currentThreadTimeMillis();
        climbEvent.eventType = Event.Cycle.CLIMB.toString();

        // Determine the level from the button label
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        climbEvent.extra = buttonText; // we use the extra field for many things based on event type

        // Climb event will change as the user selects level radio buttons, it will eventually be
        // saved to the database on Exit with the final selection, if any

    }

    public void exitPage(View view){
        // Get a reference to the database and open it
        eDB = new EventsDbAdapter(this);
        eDB.open();
        long id;  // database key id

        // Update final events in the database before leaving

        // if the climbEvent was updated by user input, save it to db
        if (climbEvent.compareTo(currentEvent) == 1) {
            // Store the event in the database
            id = eDB.insertData(climbEvent);
            if(id<=0)
            {
                // Failed to update the database
                toastPlusLog( this, "Failed to save climb event");
            }

        }

        // if slider input was provided, add each as a separate event in the events db
        if (climbTime > 0) {
            Event climbTimeEvent = new Event(Event.Phase.ENDGAME, Event.Cycle.CLIMB.toString(),
                    currentEvent.teamNum, currentEvent.match, Integer.toString(climbTime),
                    currentEvent.scoutName, currentEvent.scoutTeam);

            id = eDB.insertData(climbTimeEvent);
            if(id<=0)
            {
                // Failed to update the database
                toastPlusLog( this, "Failed to save climb time event");
            }
        }

        if (deadness > 0) {
            Event deadnessEvent = new Event(Event.Phase.ENDGAME, Event.Cycle.DEADNESS.toString(),
                    currentEvent.teamNum, currentEvent.match, Integer.toString(deadness),
                    currentEvent.scoutName, currentEvent.scoutTeam);

            id = eDB.insertData(deadnessEvent);
            if(id<=0)
            {
                // Failed to update the database
                toastPlusLog( this, "Failed to save deadness event");
            }
        }

        if (defense > 0) {
            Event defenseEvent = new Event(Event.Phase.ENDGAME, Event.Cycle.OTHER.toString(),
                    currentEvent.teamNum, currentEvent.match, "DEFENSE " + Integer.toString(defense),
                    currentEvent.scoutName, currentEvent.scoutTeam);

            id = eDB.insertData(defenseEvent);
            if(id<=0)
            {
                // Failed to update the database
                toastPlusLog( this, "Failed to save defense event");
            }
        }

        // Exit the match, return to Main Activity
        Intent intent = new Intent(this, MainActivity.class);
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

            Event commentEvent = new Event(Event.Phase.ENDGAME, Event.Cycle.COMMENT.toString(),
                    cTeam, currentEvent.match, comments, currentEvent.scoutName, currentEvent.scoutTeam);
            commentEvent.startTime = SystemClock.currentThreadTimeMillis();

            // Save the event in the database
            EventsDbAdapter eDB = new EventsDbAdapter(this);
            eDB.open();

            // Insert the event
            long id = eDB.insertData(commentEvent);

            // Could provide a better failure message later if desired, keeping as is for now as the
            // id will give the key number of the entry in the table providing a warm fuzzy on how
            // many entries are in the db
            toastPlusLog(this, "Comment saved " + Long.toString(id));
        }
        commentText.clearFocus();
    }

}
