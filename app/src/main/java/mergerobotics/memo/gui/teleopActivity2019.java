package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import mergerobotics.memo.R;
import mergerobotics.memo.dataobjects.Event;

import static mergerobotics.memo.db.EventsDbAdapter.EVENT_REF;

public class teleopActivity2019 extends AppCompatActivity {

    Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_2019);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Extract the data passed from previous activity via the intent extras
        Intent thisIntent = getIntent(); // retrieve intent once
        currentEvent = (Event) thisIntent.getSerializableExtra(EVENT_REF);
    }

    public void endgamePage(View view){
        Intent intent = new Intent(this, endgame.class);

        // Pass on the event instance to the next activity
        currentEvent.phase = Event.Phase.ENDGAME;
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);
    }

    //OnClick methods for popups
    public void cargoPickupPage(View view){
        Intent intent = new Intent(this, CargoPickupActivity.class);

        // Determine the game piece type from button label
        Button b = (Button)view;
        String buttonText = b.getText().toString();

        // Update event with game piece selected and phase
        currentEvent.phase = Event.Phase.TELEOP;
        currentEvent.eventType = buttonText;
        currentEvent.startTime = SystemClock.currentThreadTimeMillis();

        // Pass on the event instance to the next activity
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);
    }

    public void hatchPickupPage(View view){
        Intent intent = new Intent(this, HatchPickupActivity.class);

        // @TODO Determine the game piece type from button label to make more generic
        Button b = (Button)view;
        String buttonText = b.getText().toString();

        // Update event with game piece selected and phase
        currentEvent.phase = Event.Phase.TELEOP;
        currentEvent.eventType = buttonText;
        currentEvent.startTime = SystemClock.currentThreadTimeMillis();

        // Pass on the event instance to the next activity
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);
    }

}
