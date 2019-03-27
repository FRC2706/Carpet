package mergerobotics.memo.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mergerobotics.memo.R;
import android.view.View;
import android.widget.TextView;

public class TeamInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);

        TextView notes = (TextView)findViewById(R.id.textViewNotes);
        notes.setText("No Notes To Display!");
    }
}
