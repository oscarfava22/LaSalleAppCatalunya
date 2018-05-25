package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.SchoolsRepository;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.impl.SchoolsAPI;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, AdapterView.OnItemSelectedListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_LOCATION = 12345;
    private GoogleMap mMap;
    private ArrayList<CentreEscolar> centers;
    private List<Marker> centersMarkers;
    private CentreEscolar lastCenterClicked;
    private BottomSheetBehavior sheetBehavior;
    private SchoolsRepository schoolsRepo;
    private int bottomSheetState;
    private boolean firstTime;
    private boolean requestShowing;
    private int spinnerState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        schoolsRepo = new SchoolsAPI(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        centersMarkers = new ArrayList<>();
        firstTime = true;
        requestShowing = false;

        Spinner spinner = findViewById(R.id.spinner_map);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.school_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        spinner.setSelection(spinnerState);

        LinearLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);

        layoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, ActivityDescription.class);
                intent.putExtra("lastCenterClicked", lastCenterClicked);
                startActivity(intent);
            }
        });

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        sheetBehavior.setHideable(true);

        if (bottomSheetState != BottomSheetBehavior.STATE_HIDDEN) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomSheetState = BottomSheetBehavior.STATE_HIDDEN;

        } else {

            if (centers != null) {

                TextView centerName = findViewById(R.id.nom_escola_info_box);
                centerName.setText(lastCenterClicked.getNomEscola());

                TextView adressName = findViewById(R.id.adresa_escola_info_box);
                adressName.setText(lastCenterClicked.getAdresaEscola());

                ImageView image = findViewById(R.id.imatge_escola_info_box);
                image.setImageResource(R.drawable.logo_la_salle_catalunya);
            }

            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetState = BottomSheetBehavior.STATE_EXPANDED;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_map_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {

            case R.id.accedirLogin_map:
                intent = new Intent(this, LoginAdminActivity.class);
                startActivity(intent);
                break;
            case R.id.pantallaLlista_map:
                intent = new Intent(this, PantallaDeCentres.class);
                intent.putParcelableArrayListExtra("centers", centers);
                startActivity(intent);
                finish();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
       // if (asyncRequest != null) asyncRequest.cancel(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  if (asyncRequest != null) {
      //      asyncRequest.cancelDialog();
       //     asyncRequest.cancel(true);
        //}
    }

    @Override
    protected void onDestroy() {
      //  asyncRequest.context = null;
      //  asyncRequest = null;
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

            //TODO: check permissions
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (firstTime && !requestShowing) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        //En el nostre cas no cal justificar els permisos de ubicació, ja que es tracta d'una aplicació destinada a tal fi, però ho implementem igualment.
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle(R.string.permission_denied);
                        builder.setMessage(R.string.permission_denied_explanation);
                        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firstTime = false;
                            }
                        });
                        builder.setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_LOCATION);
                                requestShowing = true;
                            }
                        });
                        builder.create().show();

                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_LOCATION);
                        requestShowing = true;
                    }
                }
            } else {
                mMap.setMyLocationEnabled(true);
                int actionBarHeight = getSupportActionBar() != null? getSupportActionBar().getHeight() : 0;
                mMap.setPadding(0, actionBarHeight, 0, 0);
            }

        LatLng catalunya = new LatLng(41.3149, 2.096116);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(catalunya, 7.0f);
        googleMap.moveCamera(cameraUpdate);

        //Sol·licitar centres al Web Service.
    //    asyncRequest = new AsyncRequest(this);
      //  asyncRequest.execute();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            if (grantResults.length > 1 && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                mMap.setMyLocationEnabled(true);
                int actionBarHeight = getSupportActionBar() != null? getSupportActionBar().getHeight() : 0;
                mMap.setPadding(0, actionBarHeight, 0, 0);
            }
            firstTime = false;
            requestShowing = false;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (centers == null) return false;
        lastCenterClicked = centers.get((int) marker.getTag()); //Obtenir informació del centre associat al marker clicat.

        TextView centerName = findViewById(R.id.nom_escola_info_box);
        centerName.setText(lastCenterClicked.getNomEscola());

        TextView adressName = findViewById(R.id.adresa_escola_info_box);
        adressName.setText(lastCenterClicked.getAdresaEscola());

        ImageView image = findViewById(R.id.imatge_escola_info_box);
        image.setImageResource(R.drawable.logo_la_salle_catalunya);

        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetState = BottomSheetBehavior.STATE_EXPANDED;
        }

        return false; //False to occur the default behaviour.
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0:
                showAllCenters();
                spinnerState = 0;
                break;
            case 1:
                showSchoolCenters();
                spinnerState = 1;
                break;

            case 2:
                showOtherCenters();
                spinnerState = 2;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void deleteAllCenters () {
        if (centersMarkers != null) {
            for (int i = 0; i < centersMarkers.size(); i++) { //Esborrar els markers del mapa
                centersMarkers.get(i).remove();
            }
            centersMarkers.clear();
        }
    }

    public void showAllCenters () {
        deleteAllCenters();
        if (centers != null) {
            for (int i = 0; i < centers.size(); i++) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(centers.get(i).getLatitude(), centers.get(i).getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(centers.get(i).getColor())));
                marker.setTag(i);
                centersMarkers.add(marker);
            }
        }
    }


    public void showSchoolCenters () {
        deleteAllCenters();
        if (centers != null) {
            for (int i = 0; i < centers.size(); i++) {
                if (centers.get(i).isEsInfantil() || centers.get(i).isEsPrimaria() || centers.get(i).isEsESO()) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(centers.get(i).getLatitude(), centers.get(i).getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(centers.get(i).getColor())));
                    marker.setTag(centersMarkers.size());
                    centersMarkers.add(marker);
                }
            }
        }
    }


    public void showOtherCenters () {
        deleteAllCenters();
        if (centers != null) {
            for (int i = 0; i < centers.size(); i++) {
                if (centers.get(i).isEsBatx() || centers.get(i).isEsFP() || centers.get(i).isEsUni()) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(centers.get(i).getLatitude(), centers.get(i).getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(centers.get(i).getColor())));
                    marker.setTag(centersMarkers.size());
                    centersMarkers.add(marker);
                }
            }
        }
    }
}
