package mergerobotics.memo.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mergerobotics.memo.R;

public class Endgame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);
    }

    public void exitPage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
