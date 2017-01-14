package jialuc.cmu.edu.mortgagecal.uiinteraction;
/**
 * Name : Jialu Chen
 * Andrew ID : jialuc
 *
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jialuc.cmu.edu.mortgagecal.R;
import jialuc.cmu.edu.mortgagecal.util.DatabaseConnector;

public class AddMortgage extends Activity {

    // edit  Mortgage name, price, term and interest rate
    private EditText et_Name, et_PurchasedPrice, et_MortgageTerm, et_InterestRate;
    private DatePicker dp_FirstPaymentDate;// DatePicker
    private String firstPaymentDate; // get the first pay date
    private int startYear;  // get the start year
    private int startMonth; // get the start month
    private double monthlyPayment; // save the monthly payment
    private double totalPayment;    // save the total payment
    private String payOffDate;     // save the pay off date in String

    // create an activity
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); // call super's onCreate
        setContentView(R.layout.add_mortgage); // inflate the UI
        // get the EditText from UI
        et_Name = (EditText) findViewById(R.id.nameEditText);
        et_PurchasedPrice = (EditText) findViewById(R.id.PPEditText);
        et_MortgageTerm = (EditText) findViewById(R.id.MTEditText);
        et_InterestRate = (EditText) findViewById(R.id.IREditText);
        // get the date picker from UI
        dp_FirstPaymentDate = (DatePicker) findViewById(R.id.FPdatePicker);

        // datepicker listener
        DatePicker.OnDateChangedListener myDateListener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                //get the first payment date and save it in a string format "YYYY/MM/DD"
                String newDate;
                if ((monthOfYear < 10) && (dayOfMonth < 10))
                    newDate = year + "/" + "0" + monthOfYear + "/" + "0" + dayOfMonth;
                else if ((monthOfYear < 10) && (dayOfMonth > 9))
                    newDate = year + "/" + "0" + monthOfYear + "/" + dayOfMonth;
                else if ((monthOfYear > 9) && (dayOfMonth < 10))
                    newDate = year + "/" + monthOfYear + "/" + "0" + dayOfMonth;
                else
                    newDate = year + "/" + monthOfYear + "/" + dayOfMonth;
                // log the date in system
                Log.d("NEW_DATE", newDate);
                // save the start payment information
                setCalenderDate(newDate, year, monthOfYear);

            }
        };

        // initialize the date for date picker
        dp_FirstPaymentDate.init(2016, 0, 1, myDateListener);
        firstPaymentDate = "2016/01/01";
        startYear = 2016;
        startMonth = 1;

        // set event listener for the Save Contact Button
        Button saveContactButton =
                (Button) findViewById(R.id.saveMortgageButton);
        saveContactButton.setOnClickListener(saveMortgageButtonClicked);
    }

    // responds to event generated when user clicks the Save Button
    OnClickListener saveMortgageButtonClicked = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // test if the input is legal
            boolean testFlag = testInput();
            if (testFlag)// if legal input
            {
                calculate();
                AsyncTask<Object, Object, Object> saveContactTask =
                        new AsyncTask<Object, Object, Object>()
                        {
                            @Override
                            protected Object doInBackground(Object... params)
                            {
                                // save mortgage to the database
                                addToDataBase();
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object result)
                            {
                                // return to the previous Activity
                                finish();
                            }
                        };

                // save the mortgage to the database using a separate thread
                saveContactTask.execute((Object[]) null);
            }
            else
            {
                // create a new AlertDialog Builder
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(AddMortgage.this);

                // set dialog title & message, and provide Button to dismiss
                builder.setTitle(R.string.errorTitle);
                builder.setMessage(R.string.errorMessage);
                builder.setPositiveButton(R.string.errorButton, null);
                builder.show(); // display the Dialog
            }
        } // end method onClick
    };

    private boolean testInput(){
        double PurchasedPrice = 0;
        double termInYears = 0;
        double interestRate = 0;

        // if each input row cannot empty
        if (et_Name.getText().length() == 0 || et_PurchasedPrice.getText().toString().length() == 0 || et_MortgageTerm.getText().toString().length() == 0 ||
                et_InterestRate.getText().toString().length() == 0){
            return false;
        } else {
            PurchasedPrice = Double.parseDouble(et_PurchasedPrice.getText().toString());
            termInYears = Double.parseDouble(et_MortgageTerm.getText().toString());
            interestRate = Double.parseDouble(et_InterestRate.getText().toString());
        }

        // year limitation
        if (termInYears < 1.0 || termInYears > 99) {
            return false;
        }

        // edge cases for interest
        if (interestRate <= 0 || interestRate >= 99) {
            return false;
        }

        // edge case for purchase price
        if (PurchasedPrice <= 0) {
            return false;
        }
        return true;
    }

    private void calculate() {
        double PurchasedPrice = 0;
        double termInYears = 0;
        double interestRate = 0;

        // get the price, term and interest rate
        PurchasedPrice = Double.parseDouble(et_PurchasedPrice.getText().toString());
        termInYears = Double.parseDouble(et_MortgageTerm.getText().toString());
        interestRate = Double.parseDouble(et_InterestRate.getText().toString());

        // Convert interest rate into a decimal
        interestRate /= 100.0;

        // Calculate Monthly interest rate
        double monthlyRate = interestRate / 12.0;
        double termInMonths = termInYears * 12;

        // Calculate the monthly payment
        this.monthlyPayment =
                (PurchasedPrice * monthlyRate) /
                        (1 - Math.pow(1 + monthlyRate, -termInMonths));

        this.totalPayment = this.monthlyPayment * 12 * termInYears;
        // calculate the pay off date
        toCalculateDate();
    }

    private void toCalculateDate() {
        int termInYears = (int) Double.parseDouble(et_MortgageTerm.getText().toString());
        String payOffDate;
        int endYear = this.startYear + termInYears;
        int endMonth;
        // if start month is 12, end will be 1
        switch (this.startMonth) {
            case 1:
                endMonth = 12;
                break;

            default:
                endMonth = this.startMonth - 1;
                break;
        }

        if (endMonth < 9) {
            payOffDate = endYear + "/0" + endMonth;
        } else {
            payOffDate = endYear + "/" + endMonth;
        }
        setPayOffDate(payOffDate);
    }

    // save the payoff date
    private void setPayOffDate(String date) {
        this.payOffDate = date;
    }

    // get the time stamp
    private String getSystemTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        Date date  = c.getTime();
        String str=format.format(date);
        return str;
    }
    // set the start pay date
    private void setCalenderDate(String wholedate, int year, int month) {
        this.firstPaymentDate = wholedate;
        this.startYear = year;
        this.startMonth = month;
    }

    private void addToDataBase()
    {
        // get DatabaseConnector to interact with the SQLite database
        DatabaseConnector databaseConnector = new DatabaseConnector(this);

        databaseConnector.insertMortgage(et_Name.getText().toString(), et_PurchasedPrice.getText().toString(),
                    et_MortgageTerm.getText().toString(), et_InterestRate.getText().toString(), monthlyPayment, totalPayment,
                    firstPaymentDate, payOffDate,getSystemTime());
    }

}
