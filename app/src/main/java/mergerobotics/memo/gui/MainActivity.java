package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import mergerobotics.memo.R;
import mergerobotics.memo.dataobjects.Event;
import mergerobotics.memo.db.EventsDbAdapter;

import static mergerobotics.memo.utilities.Utilities.toastPlusLog;

public class MainActivity extends AppCompatActivity {

    // globalIntent and me are used to make it easier
    // to reference MainActivity and share relevant data
    // Ref: from powerup
    Intent globalIntent;
    public static MainActivity me;

    EditText commentText, commentTeamText;
    // Create an event to populate user input and share with subsequent activities

    public static Event eventData; // tbd, not used yet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Coming soon...", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



    }



    public void changePage(View view){
        Intent name = new Intent(this, prematchActivity.class);
        startActivity(name);
    }



    public void changeTestPage(View view){
        Intent name = new Intent(this, TestAndExport.class);
        startActivity(name);
    }

    public void changeteaminfoPage(View view){
        Intent name = new Intent(this, teamInfo.class);
        startActivity(name);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            settings();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void settings(){
            Intent name = new Intent(this, settingsGUI.class);
            startActivity(name);
    }

    public void goHandler(View view){

        // GO button has been pushed, get the user's comment data
        // if none entered provide feedback
        commentText = (EditText) findViewById(R.id.comment);
        String comments = commentText.getText().toString();

        // Create an event to store the comment if not empty, team number is mandatory
        if (!comments.matches("")) {
            commentTeamText = findViewById(R.id.teamNumber);
            String commentTeam = commentTeamText.getText().toString();

            if (commentTeam.matches("")) {
                toastPlusLog(this, "Team number required ");

            } else {
                // GUI ensures that a number was entered was entered for team
                int cTeam = Integer.parseInt(commentTeam);
                Event commentEvent = new Event(Event.Phase.PREMATCH, Event.Cycle.COMMENT.toString(),
                        cTeam, 0, comments, "unavail", 2706);
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
        else {
            toastPlusLog(this, "Enter comment before pushing GO button ");
        }
    }

}


