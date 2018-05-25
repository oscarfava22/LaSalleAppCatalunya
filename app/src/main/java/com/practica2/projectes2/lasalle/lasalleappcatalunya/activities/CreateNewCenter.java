package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.MyListViewAdapterWithOnTouch;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;
import java.util.ArrayList;
import java.util.Collections;

public class CreateNewCenter extends AppCompatActivity {

    private ArrayList<CentreEscolar> data;
    private MyListViewAdapterWithOnTouch adapter;
    private boolean orderButtonClicked;
    private Toolbar toolbar;
    private Context context;
    private static  final String SCHOOL = "school";
    private static  final String ERROR = "error";
    private static final String ACTIVITY_ADD = "ActivitAdd";
    private static final String MESSAGE_ADDCENTER = "ComingFromActivityAdd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_centers);
        context = this;
        orderButtonClicked = false;
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


      FloatingActionButton fab =  findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            android.support.v7.app.AlertDialog.Builder builder =
                    new android.support.v7.app.AlertDialog.Builder(context);

            builder.setTitle(context.getString(R.string.TitleDialogInsert));

            builder.setPositiveButton(context.getString(R.string.ADD),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(CreateNewCenter.this, ActivityAddCentre.class);
                            startActivity(intent);
                        }
                    });
            builder.setNegativeButton(context.getString(R.string.Cancel), null);

            builder.create().show();
        }
      });


        if(savedInstanceState == null) {
            data = new ArrayList<>(); //no tasks at first
            CentreEscolar centreEscolar = new CentreEscolar(); //es crea per defecte
            centreEscolar.setAdresaEscola("Carrer martin");
            centreEscolar.setEsBatx(true);
            centreEscolar.setNomEscola("Montserrat Roig");
            centreEscolar.setEsESO(true);
            centreEscolar.setDescripcio("Descripcio test montse roig");
            data.add(centreEscolar);
            ListView listView = findViewById(R.id.listview);
            adapter = new MyListViewAdapterWithOnTouch(data, this, toolbar, listView);
            listView.setAdapter(adapter);
            listView.setOnTouchListener(adapter);
        }

        Intent intent = getIntent();
        if(intent == null){
            if(intent.getExtras().getString(ACTIVITY_ADD).equals(MESSAGE_ADDCENTER)){
                if(intent.getExtras().getBoolean(ERROR)){
                    CentreEscolar c = intent.getExtras().getParcelable(SCHOOL);
                    data.add(c);
                }
            }
        }

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

        ListView listView = findViewById(R.id.listview);

        adapter = new MyListViewAdapterWithOnTouch(data, this, toolbar, listView);

        listView.setAdapter(adapter);

        listView.setOnTouchListener(adapter);


    }
}
