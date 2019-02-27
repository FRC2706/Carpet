package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import mergerobotics.memo.R;

public class CargoPickupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cargopickup);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    public void cargoPickupGround (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - pass on the button selection (pre-loaded, loading station, ground, depot)
        - launch the delivery cycle who will complete the cycle (unless user cancels)
        - dismiss the window to return to calling page
        */

        Toast myToast = Toast.makeText(this, "Cargo pickup ground",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cargo pickup ground"); // replace with db write


        Intent intent = new Intent(this, DeliveryCycleActivity.class);
        startActivity(intent);

        // Return to calling page after the delivery cycle as been completed
        finish();

    }

    public void cargoPickupLoadingStation (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - pass on the button selection (pre-loaded, loading station, ground, depot)
        - launch the delivery cycle who will complete the cycle (unless user cancels)
        - dismiss the window to return to calling page
        */

        Toast myToast = Toast.makeText(this, "Cargo pickup loading stn",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cargo pickup loading stn"); // replace with db write

        Intent intent = new Intent(this, DeliveryCycleActivity.class);
        startActivity(intent);

        // Return to calling page after the delivery cycle as been completed
        finish();

    }

    public void cargoPickupPreloaded (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - pass on the button selection (pre-loaded, loading station, ground, depot)
        - launch the delivery cycle who will complete the cycle (unless user cancels)
        - dismiss the window to return to calling page
        */

        Toast myToast = Toast.makeText(this, "Cargo pickup preloaded",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cargo pickup preloaded"); // replace with db write

        Intent intent = new Intent(this, DeliveryCycleActivity.class);
        startActivity(intent);

        // Return to calling page after the delivery cycle as been completed
        finish();

    }

    public void cargoPickupDepot (View view) {
    /*
        Each button click will
        - provide a feedback view message
        - pass on the button selection (pre-loaded, loading station, ground, depot)
        - launch the delivery cycle who will complete the cycle (unless user cancels)
        - dismiss the window to return to calling page
        */

        Toast myToast = Toast.makeText(this, "Cargo pickup depot",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cargo pickup depot");

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
        - NO DATA is saved to the database
        - dismiss the window to return to Teleop page
        */

        Toast myToast = Toast.makeText(this, "Cancelled",
                Toast.LENGTH_SHORT);
        myToast.show();
        Log.i(getClass().getName(), "Cancelled"); // remove later

        finish();

    }
}
