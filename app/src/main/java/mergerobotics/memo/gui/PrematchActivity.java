package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import mergerobotics.memo.R;

import static mergerobotics.memo.utilities.Utilities.toastPlusLog;

public class PrematchActivity extends AppCompatActivity {

    // definitions for data sharing btw activities (via putExtra)
    private static final String SCOUT_NAME = "scout";
    private static final String SCOUT_TEAM = "scoutTeam";
    private static final String TEAM = "team";
    private static final String MATCH = "match";

    // Definitions to read data from the user input
    // This data needs to be shared NB: scoutTeam not currently in GUI user input
    EditText scoutName, teamNum , matchNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prematch);
    }

    public void sandstormPage(View view){

        // Get the text views that shows the user input.

        scoutName = (EditText) findViewById(R.id.scoutName);
        String scout = scoutName.getText().toString();

        teamNum = (EditText) findViewById(R.id.teamNum);
        String team = teamNum.getText().toString();
        // Convert the EditText team number to an int
        int iTeam = Integer.parseInt(team);

        matchNum = (EditText) findViewById(R.id.matchNum);
        String match = matchNum.getText().toString();
        // Convert the EditText match number to an int
        int iMatch = Integer.parseInt(match);

        // hardcoded for now to our team, assumes no other teams are using our app
        // Can add to gui later if desired
        int scoutTeam = 2706;

        if (!scout.matches("") && !team.matches("") && !match.matches("")) {
            Intent nextIntent = new Intent(this, SandstormActivity.class);

            // echo these values before proceeding
            toastPlusLog( this, "Scout: " + scout + " Team: " + team + " Match: " + match);

            // Pass on the prematch user input to Sandstorm
            nextIntent.putExtra(SCOUT_NAME, scout);
            nextIntent.putExtra(SCOUT_TEAM, scoutTeam);
            nextIntent.putExtra(TEAM, iTeam);
            nextIntent.putExtra(MATCH, iMatch);

            startActivity(nextIntent);

            // return to main activity when teleop finishes
            finish();
        }

    }



}
