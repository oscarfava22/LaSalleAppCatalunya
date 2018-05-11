package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.view.View;
import android.widget.AdapterView;

public class SpinnerAdapter implements AdapterView.OnItemSelectedListener{

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
