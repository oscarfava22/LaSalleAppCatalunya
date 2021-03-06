package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.SpinnerAdapter;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.TabAdapter;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.fragment.ListViewFragment;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentersManager;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.SchoolsRepository;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.impl.SchoolsAPI;

import java.util.ArrayList;

public class PantallaDeCentres extends AppCompatActivity {

    private ListViewFragment tots;
    private ListViewFragment escoles;
    private ListViewFragment altres;
    private Spinner spinner;
    private static final String SCHOOLS = "schoolKey";
    private static final String FLAG = "flag";
    private SchoolsRepository schoolsRepo;
    private AsyncRequest asyncRequest;
    private boolean loadComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        setContentView(R.layout.activity_pantalla_de_centres);
        createToolbar();
        schoolsRepo = new SchoolsAPI(this);
        if(savedInstanceState != null){
            loadComplete = savedInstanceState.getBoolean(FLAG);
        }
        if(!loadComplete && CentersManager.getInstance().getCenters() == null){
            asyncRequest = new AsyncRequest(this);
            asyncRequest.execute();
        } else {
           createTabs();
           createSpinner();
        }
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pantalladecentres, menu);
        return true;
    }

    public void createSpinner(){
        spinner =  findViewById(R.id.spinner);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(tots,escoles,altres,(ArrayList<CentreEscolar>) CentersManager.getInstance().getCenters());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provincies_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(spinnerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(escoles != null && tots != null && altres != null){
            createTabs();
            createSpinner();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {

            case R.id.accedirLogin:
                intent = new Intent(this, LoginAdminActivity.class);
                startActivity(intent);
                break;

            case R.id.pantallaMapa:
                intent = new Intent(this, MapActivity.class);
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
        if (asyncRequest != null) asyncRequest.cancel(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(asyncRequest != null){
            asyncRequest.cancelDialog();
            asyncRequest.cancel(true);
        }
    }

    @Override
    protected void onDestroy() {

        if(asyncRequest != null) {
            asyncRequest.context = null;
            asyncRequest = null;
        }

        super.onDestroy();
    }

    private void createTabs(){
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewPager);

        //creating the arrays to be registered in the tabEntry
        tots = new ListViewFragment();
        altres = new ListViewFragment();
        escoles = new ListViewFragment();

        //creating all the entries
        ArrayList<TabAdapter.TabEntry> entries = new ArrayList<>();
        entries.add(new TabAdapter.TabEntry(tots, getString(R.string.All)));
        entries.add(new TabAdapter.TabEntry(escoles, getString(R.string.IPE)));
        entries.add(new TabAdapter.TabEntry(altres, getString(R.string.BFU)));

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), entries);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(FLAG, loadComplete);
        if(escoles != null && tots != null && altres != null){
            android.support.v4.app.FragmentManager fr = getSupportFragmentManager();
            FragmentTransaction transaction = fr.beginTransaction();
            transaction.remove(escoles);
            transaction.commit();
            transaction = fr.beginTransaction();
            transaction.remove(tots);
            transaction.commit();
            transaction = fr.beginTransaction();
            transaction.remove(altres);
            transaction.commit();
        }
        super.onSaveInstanceState(outState);
    }


    public ArrayList<CentreEscolar> getEscolesList() {
        return (ArrayList<CentreEscolar>) CentersManager.getInstance().getCenters();
    }

    public String getName(ListViewFragment actual){
        if(actual == tots){
            return getString(R.string.all);
        }else if(actual == escoles){
            return getString(R.string.schoola);
        }else if(actual == altres){
            return getString(R.string.othrs);
        }else{
            return "";
        }
    }

    private class AsyncRequest extends AsyncTask<String, Void, ArrayList<CentreEscolar>> {

        private Context context;
        private ProgressDialog progressDialog;

        protected AsyncRequest(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getString(R.string.wait));
            progressDialog.show();
        }

        @Override
        protected ArrayList<CentreEscolar> doInBackground(String... params) {
            return schoolsRepo.getSchools();
        }

        @Override
        protected void onPostExecute(ArrayList<CentreEscolar> aList) {
            super.onPostExecute(aList);

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (aList != null) {
                CentersManager.getInstance().setCenters(aList);
                createTabs();
                createSpinner();
                loadComplete = true;
            }

        }

        public void cancelDialog () {
            if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            cancelDialog();
        }
    }

    private class AsyncCoordinatesRequest extends AsyncTask<Void, Void, CentreEscolar> {

        private CentreEscolar centreEscolar;
        private int index;

        public AsyncCoordinatesRequest(CentreEscolar centreEscolar, int index) {
            this.centreEscolar = centreEscolar;
            this.index = index;
        }

        @Override
        protected CentreEscolar doInBackground(Void... voids) {
            return schoolsRepo.establirLocation(centreEscolar);
        }

        @Override
        protected void onPostExecute(CentreEscolar centreEscolar) {
            LatLng posicio = new LatLng(centreEscolar.getLatitude(), centreEscolar.getLongitude());
        }
    }

}

