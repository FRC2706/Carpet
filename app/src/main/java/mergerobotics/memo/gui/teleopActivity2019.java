package mergerobotics.memo.gui;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import mergerobotics.memo.R;
import mergerobotics.memo.gui.CargoPickupFragment;
import mergerobotics.memo.gui.FragmentListener;

public class teleopActivity2019 extends AppCompatActivity {

    public FragmentListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_2019);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //methods for popups

    private void showCargoPickup(){
        FragmentManager fm = getFragmentManager();
        CargoPickupFragment cargoPickupFragment = CargoPickupFragment.newInstance("subscribe", listener);
        cargoPickupFragment.show(fm, "fragment_cargopickup");
    }
}
