package jialuc.cmu.edu.geomessage.ui;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import jialuc.cmu.edu.geomessage.R;
import jialuc.cmu.edu.geomessage.exception.AutoException;

public class MainActivity extends Activity implements View.OnClickListener {

    //phone number
    private static final String NUMBER = "4123542728";
    //LNG, LAT, ALT textview
    private TextView tvLo, tvLa, tvAl;
    private Button sendButton;//button
    private Location location;
    private LocationManager locationManager;
    private String msg;


    //update location info
    private void locUpdate(Location loc) {

        if (loc != null) {
            StringBuilder longBuilder = new StringBuilder();
            StringBuilder latiBuilder = new StringBuilder();
            StringBuilder altiBuilder = new StringBuilder();
            longBuilder.append(location.getLongitude());
            latiBuilder.append(location.getLatitude());
            altiBuilder.append(location.getAltitude());
            tvLo.setText(longBuilder.toString());
            tvLa.setText(latiBuilder.toString());
            tvAl.setText(altiBuilder.toString());
            msg = "Longtitude: " + longBuilder.toString() + ", " + "Latitude: " + latiBuilder.toString() + ", " + "Altitude: " + altiBuilder.toString();
        } else {
            //toast message
            Toast.makeText(this, "Location is not available!", Toast.LENGTH_LONG).show();
            new AutoException("Location is not available!");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLo = (TextView) findViewById(R.id.tv_lon);
        tvLa = (TextView) findViewById(R.id.tv_lat);
        tvAl = (TextView) findViewById(R.id.tv_alt);

        sendButton = (Button) findViewById(R.id.btn_sendmsg);
        sendButton.setOnClickListener(MainActivity.this);

        //location service using GPS to get location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        locUpdate(location);


        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                locUpdate(locationManager.getLastKnownLocation(provider));
            }

            @Override
            public void onLocationChanged(Location location) {
            }
        });
    }

    //send message
    @Override
    public void onClick(View v) {
        SmsManager sms = SmsManager.getDefault();

        if (location != null) {
            sms.sendTextMessage(NUMBER, null, msg, null, null);
            Toast.makeText(MainActivity.this, "Message has been sent!", Toast.LENGTH_LONG).show();
        } else if(location == null){
            Toast.makeText(MainActivity.this, "No location existing!", Toast.LENGTH_LONG).show();
        }
    }

}







