package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mergerobotics.memo.R;

public class DeliveryFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_teleopdelivery);
    }

    /*public void teleopPage(View view){
        Intent intent = new Intent(this, TeleopActivity2019.class);
        startActivity(intent);
    }*/
}
