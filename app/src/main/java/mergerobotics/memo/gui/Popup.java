package mergerobotics.memo.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import mergerobotics.memo.R;

public class Popup {
    public static void fromList(final String[] options, final IPopupCaller callback, Context c)
    {
        fromList(options, callback, c, 0);
    }

    public static void fromList(final String[] options, final IPopupCaller callback, Context c, int popupID)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Pick a color");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.popupCallback(0, options[which]);
            }
        });
        builder.show();
    }
}
