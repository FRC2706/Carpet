package mergerobotics.memo.gui;

import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by Dev (John from mikes work) on 2017-01-29.
 */
public interface FragmentListener {
    public void editNameDialogCancel(DialogFragment dialogFragment);
    public void editNameDialogComplete(DialogFragment dialogFragment, Bundle data);
}

