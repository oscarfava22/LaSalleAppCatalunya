package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        setContentView(R.layout.activity_pantalla_de_centres);
        createToolbar();
        createTabs();
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pantalladecentres, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();

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
                return false;
        }
        return true;
        //TODO cuando pulsa una provincia...
    }

    private void createTabs(){
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewPager);
        //creating the arrays to be registered in the tabEntry
        tots = new ListViewFragment();
        altres = new ListViewFragment();
        escoles = new ListViewFragment();

        /* No puedes poner las arrays porque no esta la vista creada.
         //TODO temporal array
        ArrayList<CentreEscolar> centreEscolarsTemporal = new ArrayList<>();
        centreEscolarsTemporal.add(new CentreEscolar("aa","aa","bb","bb"));
        //TODO al listview deberias pasar el array de info o algo
        tots.setDataArray(centreEscolarsTemporal);
        altres.setDataArray(centreEscolarsTemporal);
        escoles.setDataArray(centreEscolarsTemporal);
         */

        //creating all the entries
        ArrayList<TabAdapter.TabEntry> entries = new ArrayList<>();
        entries.add(new TabAdapter.TabEntry(tots, getString(R.string.All)));
        //TODO cargar dadas en todos los fragmentos (las listas)
        entries.add(new TabAdapter.TabEntry(escoles, getString(R.string.IPE)));
        entries.add(new TabAdapter.TabEntry(altres, getString(R.string.BFU)));

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), entries);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
