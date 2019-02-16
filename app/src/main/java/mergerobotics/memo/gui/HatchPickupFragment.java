package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mergerobotics.memo.R;

public class HatchPickupFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hatchpickup);
    }

    /*public void teleopPage(View view){
        Intent intent = new Intent(this, TeleopActivity2019.class);
        startActivity(intent);
    }*/
}
