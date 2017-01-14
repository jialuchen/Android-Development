package jialu.cmu.edu.score.record.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import jialu.cmu.edu.score.record.R;
import jialu.cmu.edu.score.record.database.DatabaseConnector;
import jialu.cmu.edu.score.record.exception.AutoException;
import jialu.cmu.edu.score.record.util.AppUtil;

/**
 * AddActivity to add student info
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class AddActivity extends Activity implements View.OnClickListener {


    private EditText etId;
    private EditText etQ1;
    private EditText etQ2;
    private EditText etQ3;
    private EditText etQ4;
    private EditText etQ5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etId = (EditText) findViewById(R.id.et_id);
        etQ1 = (EditText) findViewById(R.id.et_q1);
        etQ2 = (EditText) findViewById(R.id.et_q2);
        etQ3 = (EditText) findViewById(R.id.et_q3);
        etQ4 = (EditText) findViewById(R.id.et_q4);
        etQ5 = (EditText) findViewById(R.id.et_q5);

        findViewById(R.id.addpage_save_button).setOnClickListener(this);

    }


    private boolean saveInMemory() {
        int id = Integer.parseInt(etId.getText().toString());
        float[] score = new float[5];

        score[0] = AppUtil.parseFloat(etQ1.getText().toString());
        score[1] = AppUtil.parseFloat(etQ2.getText().toString());
        score[2] = AppUtil.parseFloat(etQ3.getText().toString());
        score[3] = AppUtil.parseFloat(etQ4.getText().toString());
        score[4] = AppUtil.parseFloat(etQ5.getText().toString());

        return AppUtil.addScore(id, score);

    }


    private String checkInput() {

        float q1;
        float q2;
        float q3;
        float q4;
        float q5;

        if (etId.getText().length() == 0 ||
                etQ1.getText().toString().length() == 0 ||
                etQ2.getText().toString().length() == 0 ||
                etQ3.getText().toString().length() == 0 ||
                etQ4.getText().toString().length() == 0 ||
                etQ5.getText().toString().length() == 0) {
            return getString(R.string.empty_input);
        } else {
            q1 = AppUtil.parseFloat(etQ1.getText().toString());
            q2 = AppUtil.parseFloat(etQ2.getText().toString());
            q3 = AppUtil.parseFloat(etQ3.getText().toString());
            q4 = AppUtil.parseFloat(etQ4.getText().toString());
            q5 = AppUtil.parseFloat(etQ5.getText().toString());
        }

        // id must be 4 digits
        if (etId.getText().length() != 4) {
            return getString(R.string.invalid_id);
        }

        // quiz score must be between 0 to 100
        if (q1 < 0 || q1 > 100) {
            return getString(R.string.invalid_score_q1);
        }
        if (q2 < 0 || q2 > 100) {
            return getString(R.string.invalid_score_q2);
        }

        if (q3 < 0 || q3 > 100) {
            return getString(R.string.invalid_score_q3);
        }
        if (q4 < 0 || q4 > 100) {
            return getString(R.string.invalid_score_q4);
        }
        if (q5 < 0 || q5 > 100) {
            return getString(R.string.invalid_score_q5);
        }

        return null;
    }


    private void addToDataBase() {
        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        int id = Integer.parseInt(etId.getText().toString());
        float[] score = new float[5];
        score[0] = AppUtil.parseFloat(etQ1.getText().toString());
        score[1] = AppUtil.parseFloat(etQ2.getText().toString());
        score[2] = AppUtil.parseFloat(etQ3.getText().toString());
        score[3] = AppUtil.parseFloat(etQ4.getText().toString());
        score[4] = AppUtil.parseFloat(etQ5.getText().toString());
        // insert score into database
        databaseConnector.insertScore(id, score[0], score[1], score[2], score[3], score[4]);

    }


    @Override
    public void onClick(View v) {
        String error = checkInput();
        if (error == null && saveInMemory()) {
            AsyncTask<Void, Void, Void> saveContactTask =
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            addToDataBase();
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                            finish();
                        }
                    };
            saveContactTask.execute();

        } else {
            if (error == null) {
                error = "ID Exist : " + etId.getText().toString();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
            new AutoException(error, builder);
            builder.setPositiveButton(R.string.errorButton, null);
            builder.show();
        }
    }

}
