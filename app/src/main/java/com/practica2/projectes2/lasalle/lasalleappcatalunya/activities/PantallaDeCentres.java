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
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.SpinnerAdapter;
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
    private static final String CENTERS = "centers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        setContentView(R.layout.activity_pantalla_de_centres);
        createToolbar();
        if(savedInstanceState == null){
            //escolesList = getIntent().getExtras().getParcelableArrayList(CENTERS);
            //TODO test
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

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(tots,escoles,altres);

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
                finish();
                break;

            default:
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
        CentreEscolar aaa = new CentreEscolar();
        aaa.setAdresaEscola("aa");
        aaa.setProvincia("Barcelona");
        aaa.setEsInfantil(true);
        escolesList.add(aaa);
        CentreEscolar bbb = new CentreEscolar();
        bbb.setAdresaEscola("hereIm");
        bbb.setProvincia("Tarragona");
        bbb.setEsFP(true);
        escolesList.add(bbb);
        CentreEscolar ccc = new CentreEscolar();
        ccc.setAdresaEscola("aa");
        ccc.setProvincia("Lleida");
        ccc.setEsESO(true);
        escolesList.add(ccc);


        tots.setDataArray(escolesList, getString(R.string.all));
        altres.setDataArray(escolesList,getString(R.string.othrs));
        escoles.setDataArray(escolesList,getString(R.string.schoola));

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
        outState.putParcelableArrayList(SCHOOLS, escolesList);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.escolesList = savedInstanceState.getParcelableArrayList(SCHOOLS);
        createTabs();
    }
}
