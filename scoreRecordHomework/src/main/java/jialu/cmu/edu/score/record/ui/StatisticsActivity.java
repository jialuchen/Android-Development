package jialu.cmu.edu.score.record.ui;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import jialu.cmu.edu.score.record.util.AppUtil;
import jialu.cmu.edu.score.record.R;

/**
 * StatisticsActivity.
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class StatisticsActivity extends Activity {


    public float[] highestScore;
    public float[] lowestScore;
    public float[] avgScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        doCalculateStat();
        initHighestFields();
        initLowest();
        initAverage();


    }

    private void initAverage() {
        TextView average1 = (TextView) findViewById(R.id.tv_average_q1);
        TextView average2 = (TextView) findViewById(R.id.tv_average_q2);
        TextView average3 = (TextView) findViewById(R.id.tv_average_q3);
        TextView average5 = (TextView) findViewById(R.id.tv_average_q5);
        TextView average4 = (TextView) findViewById(R.id.tv_average_q4);

        average1.setText(AppUtil.formatData(avgScore[0]));
        average2.setText(AppUtil.formatData(avgScore[1]));
        average3.setText(AppUtil.formatData(avgScore[2]));
        average4.setText(AppUtil.formatData(avgScore[3]));
        average5.setText(AppUtil.formatData(avgScore[4]));
    }

    private void initLowest() {
        TextView lowest1 = (TextView) findViewById(R.id.tv_low_q1);
        TextView lowest2 = (TextView) findViewById(R.id.tv_low_q2);
        TextView lowest3 = (TextView) findViewById(R.id.tv_low_q3);
        TextView lowest4 = (TextView) findViewById(R.id.tv_low_q4);
        TextView lowest5 = (TextView) findViewById(R.id.tv_low_q5);

        lowest1.setText(AppUtil.formatData(lowestScore[0]));
        lowest2.setText(AppUtil.formatData(lowestScore[1]));
        lowest3.setText(AppUtil.formatData(lowestScore[2]));
        lowest4.setText(AppUtil.formatData(lowestScore[3]));
        lowest5.setText(AppUtil.formatData(lowestScore[4]));
    }

    private void initHighestFields() {
        TextView highest1 = (TextView) findViewById(R.id.tv_high_q1);
        TextView highest2 = (TextView) findViewById(R.id.tv_high_q2);
        TextView highest3 = (TextView) findViewById(R.id.tv_high_q3);
        TextView highest4 = (TextView) findViewById(R.id.tv_high_q4);
        TextView highest5 = (TextView) findViewById(R.id.tv_high_q5);

        highest1.setText(AppUtil.formatData(highestScore[0]));
        highest2.setText(AppUtil.formatData(highestScore[1]));
        highest3.setText(AppUtil.formatData(highestScore[2]));
        highest4.setText(AppUtil.formatData(highestScore[3]));
        highest5.setText(AppUtil.formatData(highestScore[4]));
    }


    private void doCalculateStat() {
        highestScore = new float[5];
        lowestScore = new float[5];
        avgScore = new float[5];
        if (AppUtil.saveScore == null || AppUtil.saveScore.size() == 0) {
            return;
        }
        findHighest();
        findLowest();
        findAverage();
    }

    private void findHighest() {
        for (int scoreIndex = 0; scoreIndex < highestScore.length; scoreIndex++) {
            highestScore[scoreIndex] = 0;
        }

        for (int index = 0; index < highestScore.length; index++) {
            for (Integer id : AppUtil.saveScore.keySet()) {
                if (AppUtil.saveScore.get(id)[index] > highestScore[index]) {
                    highestScore[index] = AppUtil.saveScore.get(id)[index];
                }
            }
        }
    }

    private void findLowest() {
        for (int scoreIndex = 0; scoreIndex < lowestScore.length; scoreIndex++) {
            lowestScore[scoreIndex] = 100;
        }

        for (int index = 0; index < lowestScore.length; index++) {
            for (Integer id : AppUtil.saveScore.keySet()) {
                if (AppUtil.saveScore.get(id)[index] < lowestScore[index]) {
                    lowestScore[index] = AppUtil.saveScore.get(id)[index];
                }
            }
        }
    }


    private void findAverage() {
        for (int index = 0; index < avgScore.length; index++) {
            for (Integer id : AppUtil.saveScore.keySet()) {
                avgScore[index] += AppUtil.saveScore.get(id)[index];

            }
        }
        for (int index = 0; index < avgScore.length; index++) {
            avgScore[index] /= AppUtil.saveScore.size();
        }
    }


}
