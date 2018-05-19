package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.TabAdapter;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.fragment.ListViewFragment;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;

public class PantallaDeCentres extends AppCompatActivity {

    private ListViewFragment tots;
    private ListViewFragment escoles;
    private ListViewFragment altres;
    private Spinner spinner;
    private ArrayList<CentreEscolar> escolesList;
    private static final String SCHOOLS = "schoolKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        setContentView(R.layout.activity_pantalla_de_centres);
        createToolbar();
        if(savedInstanceState == null){
            createTabs();
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

        spinner =  findViewById(R.id.spinner);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provincies_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(spinnerAdapter);
        return true;
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
                break;

            default:
                 if(spinner.getSelectedItem().toString().equals(getString(R.string.barcelona))){
                     tots.setFilteredListView(0);
                     escoles.setFilteredListView(0);
                     altres.setFilteredListView(0);
                 }
                 else if(spinner.getSelectedItem().toString().equals(getString(R.string.girona))){
                     tots.setFilteredListView(1);
                     escoles.setFilteredListView(1);
                     altres.setFilteredListView(1);
                 }
                 else if(spinner.getSelectedItem().toString().equals(getString(R.string.lleida))){
                     tots.setFilteredListView(2);
                     escoles.setFilteredListView(2);
                     altres.setFilteredListView(2);
                 }
                 else if(spinner.getSelectedItem().toString().equals(getString(R.string.tarragona))){
                     tots.setFilteredListView(3);
                     escoles.setFilteredListView(3);
                     altres.setFilteredListView(3);
                 }
                return false;
        }
        return true;
    }

    private void createTabs(){
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewPager);

        //creating the arrays to be registered in the tabEntry
        tots = new ListViewFragment();
        altres = new ListViewFragment();
        escoles = new ListViewFragment();

        escolesList = new ArrayList<>();
        CentreEscolar a = new CentreEscolar();
        a.setEsFP(true);
        a.setProvincia("Barcelona");
        CentreEscolar b = new CentreEscolar();
        b.setEsBatx(true);
        b.setProvincia("Girona");
        CentreEscolar h = new CentreEscolar();
        h.setProvincia("Lleida");
        h.setEsInfantil(true);
        CentreEscolar c = new CentreEscolar();
        c.setAdresaEscola("aa");
        c.setProvincia("Tarragona");
        c.setEsBatx(true);

        escolesList.add(c);
        escolesList.add(a);
        escolesList.add(h);
        escolesList.add(b);


        tots.setDataArray(escolesList, getString(R.string.all));
        altres.setDataArray(escolesList,getString(R.string.othrs));
        escoles.setDataArray(escolesList,getString(R.string.schoola));
        //TODO al listview deberias pasar el array de info


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
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(SCHOOLS, escolesList);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
