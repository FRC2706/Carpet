package mergerobotics.memo.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mergerobotics.memo.R;

public class MainActivity extends AppCompatActivity {
    // remove EditText scoutName, scoutTeam, teamNum , matchNum, updateold, updatenew, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Coming soon...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }



    public void changePage(View view){
        Intent name = new Intent(this, prematchActivity.class);
        startActivity(name);
    }



    public void changeTestPage(View view){
        Intent name = new Intent(this, test.class);
        startActivity(name);
    }

    public void changeteaminfoPage(View view){
        Intent name = new Intent(this, teamInfo.class);
        startActivity(name);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            settings();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void settings(){
            Intent name = new Intent(this, settingsGUI.class);
            startActivity(name);
    }

}


