package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

public class ActivityDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        CentreEscolar centreEscolar = intent.getExtras().getParcelable("lastCenterClicked");

        TextView nomEscola = (TextView) findViewById(R.id.text_view_nom_escola_activityDescription);
        nomEscola.setText(centreEscolar.getNomEscola());

        TextView address = (TextView) findViewById(R.id.text_view_adreça_activityDescription);
        address.setText(centreEscolar.getAdresaEscola());

        TextView tipusestudisLlista = (TextView) findViewById(R.id.text_view_tipus_estudis_activityDescription);
        String llistat = "";
        if(centreEscolar.isEsUni()){
            llistat.concat("HARDCODED UNIVERSITAT").concat(System.lineSeparator());
        }
        if(centreEscolar.isEsFP()){
            llistat.concat("HARDCODED FP").concat(System.lineSeparator());
        }
        if(centreEscolar.isEsBatx()){
            llistat.concat("HARDCODED BATXILLERAT").concat(System.lineSeparator());
        }
        if(centreEscolar.isEsESO()){
            llistat.concat("HARDCODED ESO").concat(System.lineSeparator());
        }
        if(centreEscolar.isEsPrimaria()){
            llistat.concat("HARDCODED PRIMARIA").concat(System.lineSeparator());
        }
        if(centreEscolar.isEsInfantil()){
            llistat.concat("HARDCODED INFANTIL").concat(System.lineSeparator());
        }
        tipusestudisLlista.setText(llistat);

        TextView descripcio = (TextView) findViewById(R.id.text_view_text_descripcio_activityDescription);
        descripcio.setText(centreEscolar.getDescripcio());

        FloatingActionButton fab =  findViewById(R.id.fab_activityDescription);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }
}
