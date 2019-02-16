package mergerobotics.memo.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Method;

import mergerobotics.memo.R;

public class Test extends AppCompatActivity implements IPopupCaller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void displayPopup(View view){
        Popup.fromList(new String[]{"HAB Level 1", "HAB Level 2", "HAB Level 3"}, this, this);
    }

    @Override
    public void popupCallback(int id, String result)
    {
        TextView tv = findViewById(R.id.textView4);
        tv.setText("You chose " + result + " from popup with id " + id);
    }

}
