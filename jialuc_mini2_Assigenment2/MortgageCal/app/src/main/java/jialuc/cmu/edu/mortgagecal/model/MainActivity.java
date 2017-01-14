package jialuc.cmu.edu.mortgagecal.model;

/**
 * Name : Jialu Chen
 * Andrew ID : jialuc
 *
 */

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import jialuc.cmu.edu.mortgagecal.R;
import jialuc.cmu.edu.mortgagecal.uiinteraction.AddMortgage;
import jialuc.cmu.edu.mortgagecal.uiinteraction.ViewMortgage;
import jialuc.cmu.edu.mortgagecal.util.DatabaseConnector;

public class MainActivity extends ListActivity {

    public static final String ROW_ID = "row_id"; // row id
    private ListView MortgageListView;  //list view
    private CursorAdapter MortgageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the list view
        MortgageListView = getListView();
        MortgageListView.setOnItemClickListener(viewMortgageListener);

        // get the array of entry by "name" from database
        String[] from = new String[] {"name"};
        int[] to  = new int[] {R.id.mainPageTextView};
        MortgageAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.ui_main,null,from,to);
        setListAdapter(MortgageAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // new GetMortageTask to execute
        new GetMortgageTask().execute((Object[]) null);
    }

    // performs database query outside GUI thread
    private class GetMortgageTask extends AsyncTask<Object, Object, Cursor>
    {
        DatabaseConnector databaseConnector =
                new DatabaseConnector(MainActivity.this);

        //database access
        @Override
        protected Cursor doInBackground(Object... params)
        {
            databaseConnector.open();

            // get all mortgage
            return databaseConnector.getAllMortgage();
        }

        // use the Cursor returned from the doInBackground method
        @Override
        protected void onPostExecute(Cursor result)
        {
            MortgageAdapter.changeCursor(result); // set the adapter's Cursor
            databaseConnector.close();
        }
    } // end class GetMortgageTask

    @Override
    protected void onStop()
    {
        Cursor cursor = MortgageAdapter.getCursor(); // get current Cursor

        if (cursor != null)
            cursor.deactivate(); // perform deactivation

        MortgageAdapter.changeCursor(null); // adapted now has no Cursor
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Jump to add mortgage page
        Intent addNewMortgage = new Intent(MainActivity.this,AddMortgage.class);
        startActivity(addNewMortgage);
        return super.onOptionsItemSelected(item);
    }

    AdapterView.OnItemClickListener viewMortgageListener = new AdapterView.OnItemClickListener() {

        // Click on each entry, jump to page with corresponding information
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // jumpt to ViewMortgage
            Intent viewMortgage = new Intent(MainActivity.this,ViewMortgage.class);

            viewMortgage.putExtra(ROW_ID,id);
            startActivity(viewMortgage);
        }
    };
}
