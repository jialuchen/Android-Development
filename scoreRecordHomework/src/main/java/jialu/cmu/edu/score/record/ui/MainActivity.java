package jialu.cmu.edu.score.record.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import jialu.cmu.edu.score.record.R;
import jialu.cmu.edu.score.record.database.DatabaseConnector;
import jialu.cmu.edu.score.record.model.ListAdapter;

/**
 * MainActivity.
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class MainActivity extends Activity implements OnClickListener {


    private ListView quizScoreListView;
    private DatabaseConnector database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        selectDB();
    }


    public void initView() {

        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_statistic).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        quizScoreListView = (ListView) findViewById(R.id.listview_id);
        database = new DatabaseConnector(MainActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }

    public void selectDB() {
        database.open();
        Cursor cursor = database.getAllScore();
        ListAdapter listAdapter = new ListAdapter(this, cursor);
        quizScoreListView.setAdapter(listAdapter);
        database.close();
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btn_add:
                intent = new Intent(this, AddActivity.class);
                break;
            case R.id.btn_statistic:
                intent = new Intent(this, StatisticsActivity.class);
                break;
            case R.id.btn_delete:
                intent = new Intent(this, DeleteActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }


}
