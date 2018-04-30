package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.TabAdapter;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.fragment.ListViewFragment;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;
import java.util.Collections;

public class PantallaDeCentres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_pantalladecentres, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.accedirLogin) {
            //TODO pantalla login admins
            return true;
        }
        if(id == R.id.pantallaMapa){
            //TODO go pantalla mapa
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createTabs(){
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewPager);

        ArrayList<TabAdapter.TabEntry> entries = new ArrayList<>();
        entries.add(new TabAdapter.TabEntry(new ListViewFragment(), getString(R.string.All)));
        //TODO cargar dadas en todos los fragmentos (las listas)
        entries.add(new TabAdapter.TabEntry(new ListViewFragment(), getString(R.string.IPE)));
        entries.add(new TabAdapter.TabEntry(new ListViewFragment(), getString(R.string.BFU)));

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), entries);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
