package com.example.sriram.route_details;

import android.location.Location;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

public class mapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,com.google.android.gms.location.LocationListener{

    private boolean mapReady;
    GoogleMap rMap ;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    LatLng myLocation;
    Location prevLocation;
    Button button1;
    double totalDist = 0.00;
    TextView textView1,textView2;
    int seccount=0;
    long mincount=0;
    boolean timer= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        textView1 = (TextView) findViewById(R.id.distance);
        textView2 = (TextView) findViewById(R.id.time);
        button1 = (Button)findViewById(R.id.startstop);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button1.getText()=="START"){
                    timer=true;
                    button1.setText("STOP");
                }
                else{
                    button1.setText("START");
                   timer=false;
                }
            }
        });
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
    }



    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest =  LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(500);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Log.i("Connected", "ram");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Connection Suspended", "ram");
    }

    @Override
    public void onLocationChanged(Location location) {
        if(timer) {
            if (seccount == 60) {
                mincount++;
                seccount = 0;
            }
            textView2.setText(new StringBuilder(String.valueOf(mincount)).append(":").append(String.valueOf(seccount)).append(" Minutes"));
            Log.i("location changed", "yes");
            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            if (seccount != 0 || mincount != 0) {
                totalDist += location.distanceTo(prevLocation);
                textView1.setText(String.format("%.2f", totalDist) + " Meters");
            }
            seccount++;
            prevLocation = location;
            rMap.addMarker(new MarkerOptions().position(myLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.images)).title("Current Position"));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myLocation, 15);
            rMap.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("failed", "connection");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        rMap = googleMap;
        rMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        rMap.setBuildingsEnabled(true);
        rMap.getUiSettings().setZoomGesturesEnabled(true);
        rMap.getUiSettings().setCompassEnabled(true);
        rMap.getUiSettings().setZoomControlsEnabled(true);
    }

}
