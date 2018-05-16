package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private static final int PERMISSIONS_REQUEST_ACCESS_LOCATION = 12345;
    private GoogleMap mMap;
    private ArrayList<CentreEscolar> centers;
    private ArrayList<LatLng> coordinates;
    private List<Marker> centersMarkers;
    private int numCenters = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        if (savedInstanceState != null) {
            //Recuperar coordinadas dels centres.
            coordinates = savedInstanceState.getParcelableArrayList("coordinates");
            numCenters = savedInstanceState.getInt("numCenters");
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);

        //TODO: check permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //En el nostre cas no cal justificar els permisos de ubicació, ja que es tracta d'una aplicació destinada a tal fi, però ho implementem igualment.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.permission_denied);
                builder.setMessage(R.string.permission_denied_explanation);
                builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_LOCATION);

                    }
                });
                builder.create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_LOCATION);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

        LatLng catalunya = new LatLng(41.3149, 2.096116);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(catalunya, 7.0f);
        googleMap.moveCamera(cameraUpdate);
        mMap.addMarker(new MarkerOptions().position(catalunya).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));


        if (numCenters > 0) { //Cal recuperar els centres.

            for (int i = 0; i < numCenters; i++) {
                //TODO: assign color depending on the type of the center
                centersMarkers.add(mMap.addMarker(new MarkerOptions().position(coordinates.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))));
            }
        } else { //Sol·licitar centres al Web Service.

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            if (grantResults.length > 1 && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("coordinates", coordinates);
        outState.putInt("numCenters", numCenters);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        TextView t = findViewById(R.id.nom_escola_item);
        t.setText("NAME");
        t.setVisibility(View.VISIBLE);
        LinearLayout l = findViewById(R.id.info_box);
        l.setVisibility(View.VISIBLE);
        return false; //False to occur the default behaviour.
    }

}
