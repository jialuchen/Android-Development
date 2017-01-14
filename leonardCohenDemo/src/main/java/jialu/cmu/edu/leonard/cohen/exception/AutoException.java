package jialu.cmu.edu.leonard.cohen.exception;

import android.app.AlertDialog;
import android.util.Log;

/**
 * AutoException
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class AutoException {
    private static final String ERROR_TITLE = "Error";

    public AutoException(String s, AlertDialog.Builder b) {
        Log.e(null, s);
        getErrorDialog(s, b);
    }

    public void getErrorDialog(String s, AlertDialog.Builder b) {
        b.setTitle(ERROR_TITLE);
        b.setMessage(s);
    }
}
