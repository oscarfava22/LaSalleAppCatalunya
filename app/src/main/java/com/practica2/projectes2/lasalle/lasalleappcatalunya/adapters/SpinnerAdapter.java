package com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.fragment.ListViewFragment;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;

public class SpinnerAdapter implements AdapterView.OnItemSelectedListener{

    private ListViewFragment tots;
    private ListViewFragment escoles;
    private ListViewFragment altres;
    private ArrayList<CentreEscolar> escolesList;

    public SpinnerAdapter(ListViewFragment tots, ListViewFragment escoles, ListViewFragment altres, ArrayList<CentreEscolar> ce) {
        this.tots = tots;
        this.escoles = escoles;
        this.altres = altres;
        this.escolesList = ce;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(escolesList == null || tots == null || escoles == null || altres == null){
            return;
        }
        switch (position) {
            case 0://Barcelona
                tots.setFilteredListView(0);
                altres.setFilteredListView(0);
                escoles.setFilteredListView(0);
                break;
            case 1://Girona
                tots.setFilteredListView(1);
                escoles.setFilteredListView(1);
                altres.setFilteredListView(1);
                break;
            case 2://Lleida
                tots.setFilteredListView(2);
                escoles.setFilteredListView(2);
                altres.setFilteredListView(2);
                break;
            case 3://Tarragona
                tots.setFilteredListView(3);
                escoles.setFilteredListView(3);
                altres.setFilteredListView(3);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
