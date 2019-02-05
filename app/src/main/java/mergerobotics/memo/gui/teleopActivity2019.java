package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mergerobotics.memo.R;

public class teleopActivity2019 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_2019);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //OnClick methods for popups
    public void pickupHatch(View view){
        Intent intent = new Intent(this, hatchPickupFragment.class);
        startActivity(intent);
    }

    public void pickupCargo(View view){
        Intent intent = new Intent(this, cargoPickupFragment.class);
        startActivity(intent);
    }

    public void gamePieceDelivery(View view){
        Intent intent = new Intent(this, deliveryFragment.class);
        startActivity(intent);
    }

}
