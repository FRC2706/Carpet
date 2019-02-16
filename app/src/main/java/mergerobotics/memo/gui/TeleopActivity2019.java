package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mergerobotics.memo.R;

public class TeleopActivity2019 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_2019);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void endgamePage(View view){
        Intent intent = new Intent(this, Endgame.class);
        startActivity(intent);
    }

    //OnClick methods for popups
    public void pickupHatch(View view){
        Intent intent = new Intent(this, HatchPickupFragment.class);
        startActivity(intent);
    }

    public void pickupCargo(View view){
        Intent intent = new Intent(this, CargoPickupFragment.class);
        startActivity(intent);
    }

    public void gamePieceDelivery(View view){
        Intent intent = new Intent(this, DeliveryFragment.class);
        startActivity(intent);
    }

}
