package mergerobotics.memo.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/* Use this class for any methods or definitions that are used frequently

 */
public  class Utilities {


    public static void toastPlusLog (Context context, String msg) {
        Toast myToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        myToast.show();
        Log.i(context.getClass().getSimpleName(), msg);
    }
}
