package mergerobotics.memo.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mergerobotics.memo.R;

public class prematchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prematch);
    }

    public void sandstormPage(View view){

        EditText scoutName = (EditText) findViewById(R.id.scoutName);
        String scout = scoutName.getText().toString();

        EditText teamNum = (EditText) findViewById(R.id.teamNum);
        String team = teamNum.getText().toString();

        EditText matchNum = (EditText) findViewById(R.id.matchNum);
        String match = matchNum.getText().toString();

        if (!scout.matches("") && !team.matches("") && !match.matches("")) {
            Intent intent = new Intent(this, sandstormActivity.class);
            startActivity(intent);
        }

    }



}
