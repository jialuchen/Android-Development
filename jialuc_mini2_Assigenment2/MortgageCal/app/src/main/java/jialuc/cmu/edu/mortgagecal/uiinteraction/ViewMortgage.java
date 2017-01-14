package jialuc.cmu.edu.mortgagecal.uiinteraction;
/**
 *
 * Name : Jialu Chen
 * Andrew ID : jialuc
 *
 */

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import jialuc.cmu.edu.mortgagecal.R;
import jialuc.cmu.edu.mortgagecal.model.MainActivity;
import jialuc.cmu.edu.mortgagecal.util.DatabaseConnector;

public class ViewMortgage extends Activity {
    private long rowID; // database row id for select
    private TextView NameTextView; // display mortgage name
    private TextView PPTextView; // displays purchase price
    private TextView MTTextView; // displays mortgage term
    private TextView IRTextView; // displays mortgage interest name
    private TextView TMPTextView; // displays mortgage total monthly pay
    private TextView TPTextView; // displays total payment
    private TextView FPDTextView; // displays first payment date
    private TextView PODTextView; // displays pay off date
    private TextView LOGTextView; // displays added log time

    // called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mortgage);

        // get the TextView for all output
        NameTextView = (TextView) findViewById(R.id.NAMETextView);
        PPTextView = (TextView) findViewById(R.id.PPTextView);
        MTTextView = (TextView) findViewById(R.id.MTTextView);
        IRTextView = (TextView) findViewById(R.id.IRTextView);
        TMPTextView = (TextView) findViewById(R.id.TMPTextView);
        TPTextView = (TextView) findViewById(R.id.TPTextView);
        FPDTextView = (TextView) findViewById(R.id.FPDTextView);
        PODTextView = (TextView) findViewById(R.id.PODTextView);
        LOGTextView = (TextView) findViewById(R.id.LOGTextView);
        // get the selected contact's row ID
        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong(MainActivity.ROW_ID);
    }

    // called when the activity is resumed
    @Override
    protected void onResume()
    {
        super.onResume();
        // create new LoadContactTask and execute it
        new LoadMortgageTask().execute(rowID);
    }

    //  database query in a helper
    private class LoadMortgageTask extends AsyncTask<Long, Object, Cursor>
    {
        DatabaseConnector databaseConnector =
                new DatabaseConnector(ViewMortgage.this);

        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params)
        {
            databaseConnector.open();

            // get all data on given entry
            return databaseConnector.getOneMortgage(params[0]);
        }

        // use the Cursor returned from the doInBackground method
        @Override
        protected void onPostExecute(Cursor result)
        {
            super.onPostExecute(result);
            result.moveToFirst();
            // get the column index for each data item
            int NAMEIndex = result.getColumnIndex(DatabaseConnector.NAME);
            int PPIndex = result.getColumnIndex(DatabaseConnector.PP);
            int MTIndex = result.getColumnIndex(DatabaseConnector.MT);
            int IRIndex = result.getColumnIndex(DatabaseConnector.IR);
            int TMPIndex = result.getColumnIndex(DatabaseConnector.TMP);
            int TPIndex = result.getColumnIndex(DatabaseConnector.TP);
            int FPDIndex = result.getColumnIndex(DatabaseConnector.FPD);
            int PODIndex = result.getColumnIndex(DatabaseConnector.POD);
            int LOGIndex = result.getColumnIndex(DatabaseConnector.TIME);

            // fill TextViews with the retrieved data
            NameTextView.setText(result.getString(NAMEIndex));
            PPTextView.setText(result.getString(PPIndex));
            MTTextView.setText(result.getString(MTIndex));
            IRTextView.setText(result.getString(IRIndex));
            TMPTextView.setText(result.getString(TMPIndex));
            TPTextView.setText(result.getString(TPIndex));
            FPDTextView.setText(result.getString(FPDIndex));
            PODTextView.setText(result.getString(PODIndex));
            LOGTextView.setText(result.getString(LOGIndex));
            result.close(); // close the result cursor
            databaseConnector.close(); // close database connection
        } // end method onPostExecute
    } // end class LoadContactTask
}
