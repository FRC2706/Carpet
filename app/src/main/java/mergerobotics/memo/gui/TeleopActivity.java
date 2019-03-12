package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mergerobotics.memo.R;
import mergerobotics.memo.dataobjects.Event;

import static mergerobotics.memo.db.EventsDbAdapter.EVENT_REF;

public class TeleopActivity extends AppCompatActivity {

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

        final TextView tvGameTime = (TextView) findViewById(R.id.timer_textView);
        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {

                if (remainTime == 0) {
                    tvGameTime.setText("Time Up");
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

        // @TODO Determine the game piece type from button label to make more generic
        Button b = (Button)view;
        String buttonText = b.getText().toString();

        // Update event with game piece selected
        currentEvent.eventType = buttonText;
        currentEvent.startTime = SystemClock.currentThreadTimeMillis();

        // Pass on the event instance to the next activity
        intent.putExtra(EVENT_REF, currentEvent);
        startActivity(intent);
    }

}
