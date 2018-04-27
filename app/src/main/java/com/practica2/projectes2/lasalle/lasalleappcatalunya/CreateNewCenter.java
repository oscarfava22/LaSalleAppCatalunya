package com.practica2.projectes2.lasalle.lasalleappcatalunya;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.MyListViewAdapter;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;
import java.util.Collections;

public class CreateNewCenter extends AppCompatActivity {

    private ArrayList<CentreEscolar> data;
    private MyListViewAdapter adapter;
    private boolean orderButtonClicked;
    private Context context;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_centers);

        orderButtonClicked = false;

        Toolbar toolbar = findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar); //error

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ir a la vista de añadir escuela
            }
        });

        if(savedInstanceState == null){
            data = new ArrayList<>(); //no tasks at first
            data.add(new CentreEscolar("nomescola","adresa","infantil"));
            adapter = new MyListViewAdapter(data, this, toolbar);

            ListView listView = findViewById(R.id.listview);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.orderAscDesc) {
            //ordenar
            if(!orderButtonClicked){
                Collections.sort(data);
            }else {
                Collections.sort(data, CentreEscolar.COMPARATOR_ALPHABETIC);
                orderButtonClicked = false;
            }
            adapter.notifyDataSetChanged();
            return true;
        }
        if(id == R.id.logout){
            //logout
            onStop();
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("key", data);
        outState.putBoolean("keyTwo", orderButtonClicked);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        data = savedInstanceState.getParcelableArrayList("key");
        orderButtonClicked = savedInstanceState.getBoolean("keyTwo");

        adapter = new MyListViewAdapter(data, this, toolbar);

        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(adapter);

    }
}
