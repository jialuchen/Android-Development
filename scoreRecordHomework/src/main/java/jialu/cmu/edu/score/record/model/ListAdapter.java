package jialu.cmu.edu.score.record.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import jialu.cmu.edu.score.record.database.DatabaseConnector;
import jialu.cmu.edu.score.record.util.AppUtil;
import jialu.cmu.edu.score.record.R;
/**
 * ListAdapter
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class ListAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;


    public ListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }


    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cursor.moveToPosition(position);


        @SuppressLint({"ViewHolder", "InflateParams"})
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.list_table, null);

        TextView tvStudentId = (TextView) layout.findViewById(R.id.tv_id);
        TextView tvQ1 = (TextView) layout.findViewById(R.id.tv_q1);
        TextView tvQ2 = (TextView) layout.findViewById(R.id.tv_q2);
        TextView tvQ3 = (TextView) layout.findViewById(R.id.tv_q3);
        TextView tvQ4 = (TextView) layout.findViewById(R.id.tv_q4);
        TextView tvQ5 = (TextView) layout.findViewById(R.id.tv_q5);


        String studentId = cursor.getString(cursor.getColumnIndex(DatabaseConnector.ID));
        String q1 = cursor.getString(cursor.getColumnIndex(DatabaseConnector.Q1));
        String q2 = cursor.getString(cursor.getColumnIndex(DatabaseConnector.Q2));
        String q3 = cursor.getString(cursor.getColumnIndex(DatabaseConnector.Q3));
        String q4 = cursor.getString(cursor.getColumnIndex(DatabaseConnector.Q4));
        String q5 = cursor.getString(cursor.getColumnIndex(DatabaseConnector.Q5));


        int id = Integer.parseInt(studentId);
        if (!AppUtil.isIDExist(id)) {
            AppUtil.addScore(id, new float[]{AppUtil.parseFloat(q1),
                    AppUtil.parseFloat(q2), AppUtil.parseFloat(q3), AppUtil.parseFloat(q4), AppUtil.parseFloat(q5)});
        }

        if (studentId.length() < 4) {
            studentId = 0 + studentId;
        }
        tvStudentId.setText(studentId);


        tvQ1.setText(q1);
        tvQ2.setText(q2);
        tvQ3.setText(q3);
        tvQ4.setText(q4);
        tvQ5.setText(q5);
        return layout;
    }
}
