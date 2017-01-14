package jialu.cmu.edu.score.record.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import jialu.cmu.edu.score.record.database.DatabaseConnector;
import jialu.cmu.edu.score.record.exception.AutoException;
import jialu.cmu.edu.score.record.util.AppUtil;
import jialu.cmu.edu.score.record.R;
/**
 * DeleteActivity to delete student info
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */

public class DeleteActivity extends Activity implements View.OnClickListener {

    private EditText etId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        etId = (EditText) findViewById(R.id.et_id_id);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String testFlag = checkInput();
        if (testFlag == null) {
            deleteOneScore();
            AsyncTask<Void, Void, Void> saveContactTask =
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            deleteOneFromDatabase();
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                            finish();
                        }
                    };

            saveContactTask.execute();
        } else {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(DeleteActivity.this);
            new AutoException(testFlag, builder);
            builder.setPositiveButton(R.string.errorButton, null);
            builder.show();
        }
    }


    private String checkInput() {
        int id;
        if (etId.getText().length() != 4) {
            return getString(R.string.invalid_id);
        } else {
            id = Integer.parseInt(etId.getText().toString());
        }

        if (!AppUtil.isIDExist(id)) {
            return "ID : " + id + " Not Exist";
        }

        return null;
    }

    private void deleteOneScore() {
        int id = Integer.parseInt(etId.getText().toString());
        if (!AppUtil.isIDExist(id)) {
            return;
        }
        AppUtil.deleteOneScore(id);
    }

    private void deleteOneFromDatabase() {
        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        int id = Integer.parseInt(etId.getText().toString());
        databaseConnector.deleteOne(id);

    }


}
