package com.cloudproj.microforum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class NewForum extends AppCompatActivity implements LocationListener {

    private final String reqUrl = "http://thelastmoment-homework.appspot.com/";
    private static final int SEND_INTERVAL = 1000 * 20;
    private long DISTANCE_FOR_UPDATE= 0;
    private static final float TIME_FOR_UPDATE = 1000* 5;
    private int MY_PERMISSION_ACCESS_COURSE_LOCATION = 0;

    private Context mContext;
    protected LocationManager locationManager;
    boolean chk_gps = false;
    boolean chk_network = false;
    boolean canGetLocation = false;

    Spinner categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_forum);
        mContext = getBaseContext();

        final Location loc = getLocation();
        if(loc!=null){
            findViewById(R.id.etLocation).setEnabled(false);
            ((EditText)findViewById(R.id.etLocation)).setText(String.valueOf(loc.getLatitude())+
                                ";" + String.valueOf(loc.getLatitude()));
        }

        categories = (Spinner) findViewById(R.id.category);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);

        Button btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnClickPost();
            }
        });
    }

    public void OnClickPost(){

        String category = categories.getSelectedItem().toString();
        String question = ((EditText) findViewById(R.id.etQuestion)).getText().toString();
        String radius = ((EditText) findViewById(R.id.etRadius)).getText().toString();
        String latitude = ((EditText)findViewById(R.id.etLocation)).getText().toString().split(";")[0];
        String longitude = ((EditText)findViewById(R.id.etLocation)).getText().toString().split(";")[1];

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.SharedPref), MODE_PRIVATE);
        String userID = prefs.getString("UserID", null);

        try {
            //AddNewForum(category, latitude, longitude, radius, userID, question);
            Toast.makeText(getApplicationContext(),"POSTED",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), ForumPage.class);
            i.putExtra("Question", question);
            startActivity(i);
        }
        catch (Exception io){
            io.printStackTrace();
        }
    }

    public void AddNewForum(String category, String latitude, String longitude, String radius, String userID, String question)
            throws IOException {

        try {
            // may want to set read and connect timeouts
            Map<String,Object> params = new LinkedHashMap<>();
            params.put(getResources().getString(R.string.forum_latitude),latitude );
            params.put(getResources().getString(R.string.forum_longitude),longitude );
            params.put(getResources().getString(R.string.forum_radius),radius );
            params.put(getResources().getString(R.string.forum_AuthorID),userID );
            params.put(getResources().getString(R.string.forum_question),question);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            URL url = new URL(reqUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true); // HTTP POST Request
            connection.setConnectTimeout(SEND_INTERVAL);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.setDoOutput(true);
            connection.getOutputStream().write(postDataBytes);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        finally {

        }
    }

    public Location getLocation(){
        Location location = new Location("dummyprovider");
        try{
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            chk_gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            chk_network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!chk_gps && !chk_network){
                Log.d("Connection_problem", "Check connection");
            }
            else{
                this.canGetLocation = true;

                if(chk_gps){
                    if(location!=null){

                        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSION_ACCESS_COURSE_LOCATION);
                        }

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,DISTANCE_FOR_UPDATE,TIME_FOR_UPDATE,this);
                        if(locationManager!=null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }

                    }
                }

                else if(chk_network) {

                    if (location != null) {

                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSION_ACCESS_COURSE_LOCATION);
                        }

                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, DISTANCE_FOR_UPDATE, TIME_FOR_UPDATE, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
