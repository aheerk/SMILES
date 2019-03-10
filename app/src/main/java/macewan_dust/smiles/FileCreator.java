package macewan_dust.smiles;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCreator {

    public static final String TAG = "FileCreator";

    /* Checks if external storage is available for read and write
     * Source: https://developer.android.com/training/data-storage/files#java
     *
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * @return
     * source: https://developer.android.com/training/data-storage/files#java
     */
    public static File getPublicStorageDir() {
        // Get the directory for the user's public pictures directory.
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!path.exists()) {
            Log.e(TAG, "Directory not created");
        }
        return path;
    }

    // https://developer.android.com/reference/android/os/Environment.html#getExternalStoragePublicDirectory(java.lang.String)
}
