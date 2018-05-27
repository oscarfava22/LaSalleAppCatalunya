package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.MyListViewAdapterWithOnTouch;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentersManager;
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
            data = (ArrayList<CentreEscolar>) CentersManager.getInstance().getCenters(); //no tasks at first
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_createnewcenter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.orderAscDesc) {
            //ordenar
            if(!orderButtonClicked){
                Collections.sort(data);
                orderButtonClicked = true;
            }else {
                Collections.sort(data, CentreEscolar.COMPARATOR_ALPHABETIC);
                orderButtonClicked = false;
            }
            adapter.notifyDataSetChanged();
            return true;
        }
        if(id == R.id.logout){
            adapter.notifyDataSetChanged();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("keyTwo", orderButtonClicked);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        data = (ArrayList<CentreEscolar>) CentersManager.getInstance().getCenters();
        orderButtonClicked = savedInstanceState.getBoolean("keyTwo");

        ListView listView = findViewById(R.id.listview);

        adapter = new MyListViewAdapterWithOnTouch(data, this, toolbar, listView);

        listView.setAdapter(adapter);

        listView.setOnTouchListener(adapter);


    }
}
