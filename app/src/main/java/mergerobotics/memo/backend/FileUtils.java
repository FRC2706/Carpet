package mergerobotics.memo.backend;

import android.os.Environment;

import java.io.File;

public class FileUtils {

    public static void verifyBaseDir()
    {
        File f = new File(getBaseDir());
        f.mkdirs();
    }


    public static String getBaseDir()
    {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        return path.getAbsolutePath() + "/MEMO/";
    }
}
