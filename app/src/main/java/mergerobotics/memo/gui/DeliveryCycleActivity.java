package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mergerobotics.memo.R;

public class DeliveryCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_teleopdelivery);

    }

    public void deliverCargoToShip (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database
        - dismiss the window to return to Teleop page
        */
        Toast myToast = Toast.makeText(this, "Teleop Delivery to Cargo Ship",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Teleop Delivery to Cargo Ship");

        // Return to Teleop page, do we want a slight delay ?
        finish();

    }

    public void deliverCargoToRocketLvl1 (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database
        - dismiss the window to return to Teleop page
        */
        Toast myToast = Toast.makeText(this, "Teleop Delivery to Rocket Ship lvl 1",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Teleop Delivery to Rocket Ship lvl 1");

        // Return to Teleop page, do we want a slight delay ?
        finish();
    }


    public void deliverCargoToRocketLvl2 (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database
        - dismiss the window to return to Teleop page
        */
        Toast myToast = Toast.makeText(this, "Teleop Delivery to Rocket Ship lvl 2",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Teleop Delivery to Rocket Ship lvl 2");

        // Return to Teleop page, do we want a slight delay ?
        finish();
    }

    public void deliverCargoToRocketLvl3 (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database
        - dismiss the window to return to Teleop page
        */
        Toast myToast = Toast.makeText(this, "Teleop Delivery to Rocket Ship lvl 3",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Teleop Delivery to Rocket Ship lvl 3");

        // Return to Teleop page, do we want a slight delay ?
        finish();
    }

    public void deliveryDropped (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - write to the events database
        - dismiss the window to return to Teleop page
        */
        Toast myToast = Toast.makeText(this, "Teleop Delivery dropped",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Teleop Delivery dropped");

        // Return to Teleop page, do we want a slight delay ?
        finish();
    }

    public void cancelled (View view) {
    /*
        This is a generic method to use when a scout clicks a cancel button in any of our
        nested activities, similar to back Note: needs to be in context, hence repeated per
        Activity for now
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
