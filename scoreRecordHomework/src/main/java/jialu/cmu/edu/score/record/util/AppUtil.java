package jialu.cmu.edu.score.record.util;

import android.annotation.SuppressLint;

import java.util.LinkedHashMap;

/**
 * AppUtil.
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class AppUtil {
    public static LinkedHashMap<Integer, float[]> saveScore;

    public static boolean addScore(int id, float[] score) {


        if (saveScore == null) {
            saveScore = new LinkedHashMap<>();
            saveScore.put(id, score);
        } else {
            if (saveScore.get(id) != null) {
                return false;
            }
            saveScore.put(id, score);
        }
        return true;
    }

    public static void deleteOneScore(int id) {
        if (saveScore == null) {
            return;
        }

        saveScore.remove(id);

    }

    public static void deleteAllScore() {
        if (saveScore == null) {
            return;
        }
        saveScore = new LinkedHashMap<>();

    }

    public static boolean isIDExist(int id) {
        if (saveScore == null) {
            return false;
        }

        if (saveScore.get(id) == null) {
            return false;
        }
        return true;
    }

    @SuppressLint("DefaultLocale")
    public static String formatData(float v) {
        return String.format("%.2f", v);
    }

    public static float parseFloat(String string) {
        return Float.parseFloat(string);
    }
}
