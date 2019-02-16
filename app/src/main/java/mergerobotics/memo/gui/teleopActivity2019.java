package mergerobotics.memo.gui;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import mergerobotics.memo.R;
import mergerobotics.memo.gui.CargoPickupFragment;
import mergerobotics.memo.gui.FragmentListener;

public class teleopActivity2019 extends AppCompatActivity implements FragmentListener{

    public FragmentListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_2019);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void editNameDialogComplete(android.app.DialogFragment dialogFragment, Bundle data){

        /*
        if(dialogFragment instanceof DroppedCubeFragment) {
            CubeDroppedEvent cubeDroppedEvent = (CubeDroppedEvent) data.getSerializable("dropped_cube");
            cubeDroppedEvent.timestamp = event.timestamp;
            teleopScoutingObject.add(cubeDroppedEvent);

        }*/
    }

    @Override
    public void editNameDialogCancel(DialogFragment dialogFragment){
        dialogFragment.dismiss();
    }

    //methods for popups
    private void showCargoPickup(){
        FragmentManager fm = getFragmentManager();
        CargoPickupFragment cargoPickupFragment = CargoPickupFragment.newInstance("subscribe", this);
        cargoPickupFragment.show(fm, "fragment_cargopickup");
    }
}
