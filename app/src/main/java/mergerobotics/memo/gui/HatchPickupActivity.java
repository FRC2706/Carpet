package mergerobotics.memo.gui;

import android.content.Intent;
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

    }

    public void hatchPickupGround (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - pass on the button selection (pre-loaded, loading station, ground)
        - launch the delivery cycle who will complete the cycle (unless user cancels)
        - dismiss the window to return to calling page
        */

        Toast myToast = Toast.makeText(this, "Hatch pickup ground",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Hatch pickup ground");

        Intent intent = new Intent(this, DeliveryCycleActivity.class);
        startActivity(intent);

        // Return to calling page after the delivery cycle as been completed
        finish();

    }

    public void hatchPickupLoadingStation (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - pass on the button selection (pre-loaded, loading station, ground)
        - launch the delivery cycle who will complete the cycle (unless user cancels)
        - dismiss the window to return to calling page
        */

        Toast myToast = Toast.makeText(this, "Hatch pickup loading stn",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Hatch pickup loading stn");

        Intent intent = new Intent(this, DeliveryCycleActivity.class);
        startActivity(intent);

        // Return to calling page after the delivery cycle as been completed
        finish();

    }

    public void hatchPickupPreloaded (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - pass on the button selection (pre-loaded, loading station, ground)
        - launch the delivery cycle who will complete the cycle (unless user cancels)
        - dismiss the window to return to calling page
        */

        Toast myToast = Toast.makeText(this, "Hatch pickup preloaded",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Hatch pickup preloaded");

        Intent intent = new Intent(this, DeliveryCycleActivity.class);
        startActivity(intent);

        // Return to calling page after the delivery cycle as been completed
        finish();

    }

    public void cancelled (View view) {
    /*
        This is a generic method to use when a scout clicks a cancel button in any of our
        nested activities, similar to back
        - provide a feedback view message
        - NO DATA is saved in the database
        - dismiss the window to return to Teleop page
        */

        Toast myToast = Toast.makeText(this, "Cancelled",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cancelled"); // remove later

        finish();

    }
}
