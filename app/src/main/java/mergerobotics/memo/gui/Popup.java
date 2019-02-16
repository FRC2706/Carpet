package mergerobotics.memo.gui;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import java.util.function.Function;

import mergerobotics.memo.R;

public class Popup {
    public static View inflateLayout(int layout, Context c)
    {
        LayoutInflater inflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflator.inflate(layout, null);
    }

    public static View fromLayout(int layout, Context c, final Runnable callback)
    {
        View root = inflateLayout(layout, c);
        PopupWindow pw = new PopupWindow(root);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                callback.run();
            }
        });

        return root;
    }
}
