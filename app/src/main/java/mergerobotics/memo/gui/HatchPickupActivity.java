package mergerobotics.memo.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import mergerobotics.memo.R;

public class HatchPickupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hatchpickup);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    public void hatchPickupGround (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database (pre-loaded, loading station, ground)
        - dismiss the window to return to Teleop page
        */

        Toast myToast = Toast.makeText(this, "Hatch pickup ground",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Hatch pickup ground"); // replace with db write

        // Return to Teleop page, do we want a slight delay ?
        finish();

    }

    public void hatchPickupLoadingStation (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database (pre-loaded, loading station, ground)
        - dismiss the window to return to Teleop page
        */

        Toast myToast = Toast.makeText(this, "Hatch pickup loading stn",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Hatch pickup loading stn"); // replace with db write

        // Return to Teleop page, do we want a slight delay ?
        finish();

    }

    public void hatchPickupPreloaded (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database (pre-loaded, loading station, ground)
        - dismiss the window to return to Teleop page
        */

        Toast myToast = Toast.makeText(this, "Hatch pickup preloaded",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Hatch pickup preloaded"); // replace with db write

        // Return to Teleop page, do we want a slight delay ?
        finish();

    }


    public void cancelled (View view) {
    /*
        This is a generic method to use when a scout clicks a cancel button in any of our
        nested activities, similar to back
        - provide a feedback view message
        - dismiss the window to return to Teleop page
        */

        Toast myToast = Toast.makeText(this, "Cancelled",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cancelled"); // remove later

        finish();

    }
}
