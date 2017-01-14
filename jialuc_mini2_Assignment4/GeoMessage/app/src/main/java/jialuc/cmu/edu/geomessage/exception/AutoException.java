package jialuc.cmu.edu.geomessage.exception;
import android.util.Log;

/**
 * AutoException
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class AutoException {

    private final String ERROR_TITLE = "ERROR";

    // Constructor
    public AutoException(String error) {
        Log.e(ERROR_TITLE, error);
    }



}
