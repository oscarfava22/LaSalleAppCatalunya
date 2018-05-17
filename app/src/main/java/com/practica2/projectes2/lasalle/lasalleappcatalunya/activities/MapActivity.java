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
    private List<CentreEscolar> centers;
    private ArrayList<LatLng> coordinates;
    private List<Marker> centersMarkers;
    private int numCenters = 0;

    SchoolsRepository schoolsRepo;

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

        if (savedInstanceState != null) {
            //Recuperar coordinadas dels centres.
            coordinates = savedInstanceState.getParcelableArrayList("coordinates");
            numCenters = savedInstanceState.getInt("numCenters");
        } else {
            centersMarkers = new ArrayList<>();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_map_activity, menu);

        MenuItem item = menu.findItem(R.id.spinner_map);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.school_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
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
                startActivity(intent);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

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

        if (numCenters > 0) { //Cal recuperar els centres.

            for (int i = 0; i < numCenters; i++) {
                //TODO: assign color depending on the type of the center
                Marker marker = mMap.addMarker(new MarkerOptions().position(coordinates.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                marker.setTag(i);
                centersMarkers.add(marker);
            }
        } else { //Sol·licitar centres al Web Service.
            //SchoolsAPI schoolsAPI = new SchoolsAPI();
            //centers = schoolsAPI.getSchools();
            //numCenters = centers == null ? 0: centers.size();
            new AsyncRequest(this).execute();
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

        if (centers == null) return false;
        CentreEscolar centre = centers.get((int) marker.getTag()); //Obtenir informació del centre associat al marker clicat.

        TextView centerName = findViewById(R.id.nom_escola_info_box);
        centerName.setText(centre.getNomEscola());

        TextView adressName = findViewById(R.id.adresa_escola_info_box);
        adressName.setText(centre.getAdresaEscola());

        ImageView image = findViewById(R.id.imatge_escola_info_box);
        image.setImageResource(R.drawable.logo_la_salle_catalunya);
        //TODO: carregar imatge

        LinearLayout l = findViewById(R.id.info_box);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, ActivityDescription.class);
                startActivity(intent);
            }
        });
        l.setVisibility(View.VISIBLE);
        return false; //False to occur the default behaviour.
    }

    @Override
    public void onMapClick(LatLng latLng) {
        LinearLayout l = findViewById(R.id.info_box);
        l.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0:
                showAllCenters();
                break;
            case 1:
                showSchoolCenters();
                break;

            case 2:
                showOtherCenters();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class AsyncRequest extends AsyncTask<String, Void, List<CentreEscolar>> {

        private Context context;
        private ProgressDialog progressDialog;

        protected AsyncRequest(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("HARDCODED");
            progressDialog.show();
        }

        @Override
        protected List<CentreEscolar> doInBackground(String... params) {
            return schoolsRepo.getSchools();
        }

        @Override
        protected void onPostExecute(List<CentreEscolar> aList) {
            super.onPostExecute(aList);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            centers = aList;
            if (centers != null) {
                numCenters = aList.size();
                for (int i = 0; i < centers.size(); i++) {
                    schoolsRepo.establirLocation(centers.get(i));
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(centers.get(i).getLatitude(), centers.get(i).getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(centers.get(i).getColor())));
                    marker.setTag(i);
                    centersMarkers.add(marker);
                }
            }
            /*
            adapter = new MoviesListViewAdapter(aList, context);
            ListView listView = (ListView) findViewById(R.id.moviesListView);
            listView.setAdapter(adapter);
            */
        }
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
                if (!centers.get(i).isEsInfantil() && !centers.get(i).isEsPrimaria() && !centers.get(i).isEsESO()) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(centers.get(i).getLatitude(), centers.get(i).getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(centers.get(i).getColor())));
                    marker.setTag(centersMarkers.size());
                    centersMarkers.add(marker);
                }
            }
        }
    }


}
