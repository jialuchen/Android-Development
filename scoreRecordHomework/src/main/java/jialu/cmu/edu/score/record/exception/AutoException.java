package jialu.cmu.edu.score.record.exception;


import android.app.AlertDialog;
/**
 * AutoException
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class AutoException {

    private final String ERROR_TITLE = "ERROR";

    // Constructor
    public AutoException(String s, AlertDialog.Builder b) {
        buildErrorDialog(s, b);
    }


    public void buildErrorDialog(String s, AlertDialog.Builder b) {
        b.setTitle(ERROR_TITLE);
        b.setMessage(s);
    }

}
