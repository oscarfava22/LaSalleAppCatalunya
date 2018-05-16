package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.view.View;
import android.widget.AdapterView;

public class SpinnerAdapter implements AdapterView.OnItemSelectedListener{

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0://Barcelona
                break;
            case 1://Girona
                break;
            case 2://Lleida
                break;
            case 3://Tarragona
                break;
        }
        //TODO avisar totes les tabs del canvi
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
