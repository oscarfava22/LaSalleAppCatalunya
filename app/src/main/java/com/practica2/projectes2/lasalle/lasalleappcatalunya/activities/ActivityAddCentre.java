package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.SchoolsRepository;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.impl.SchoolsAPI;

public class ActivityAddCentre extends AppCompatActivity {

    private CentreEscolar centreEscolar = new CentreEscolar();

    private SchoolsRepository schoolsRepo;

    private static  final String SCHOOL = "school";
    private static  final String ERROR = "error";
    private static final String ACTIVITY_ADD = "ActivitAdd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_centre);

        final Toolbar toolbar = findViewById(R.id.toolbar_activity_add_centre);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_add_new_centre));

        schoolsRepo = new SchoolsAPI(this);

    }


    public void onClickAdd(View view){
        boolean error = false;
        TextInputEditText tietSchoolName = findViewById(R.id.tiet_activity_add_center_school_name);
        TextInputEditText tietSchoolAddress = findViewById(R.id.tiet_activity_add_center_school_address);
        TextInputEditText tietDescription = findViewById(R.id.tiet_activity_add_center_school_description);

        CheckBox cbUni = findViewById((R.id.cb_uni));
        CheckBox cbFP = findViewById((R.id.cb_FP));
        CheckBox cbBatx = findViewById((R.id.cb_batx));
        CheckBox cbEso = findViewById((R.id.cb_eso));
        CheckBox cbPrimaria = findViewById((R.id.cb_primaria));
        CheckBox cbInfantil = findViewById((R.id.cb_infantil));

        Spinner spProvincia = findViewById((R.id.spinner_activity_add_center_provincia));

        if(tietSchoolName.getText().length() == 0){
            error = true;
            tietSchoolName.setError(getString(R.string.errorEmpty));
        }
        if(tietSchoolAddress.getText().length() == 0){
            error = true;
            tietSchoolName.setError(getString(R.string.errorEmpty));
        }
        if(tietDescription.getText().length() == 0){
            error = true;
            tietSchoolName.setError(getString(R.string.errorEmpty));
        }
        if(!cbUni.isChecked() && !cbFP.isChecked() && !cbBatx.isChecked() && !cbEso.isChecked() &&
                !cbPrimaria.isChecked() && !cbInfantil.isChecked() &&!error){
            error = true;
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle(getString(R.string.errorTitle))
                    .setMessage(getString(R.string.errorCourses))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if(!error) {
            centreEscolar.setNomEscola(tietSchoolName.getText().toString());
            centreEscolar.setProvincia(spProvincia.getSelectedItem().toString());
            centreEscolar.setAdresaEscola(tietSchoolAddress.getText().toString());
            centreEscolar.setDescripcio(tietDescription.getText().toString());
            centreEscolar.setEsUni(cbUni.isChecked());
            centreEscolar.setEsFP(cbFP.isChecked());
            centreEscolar.setEsBatx(cbBatx.isChecked());
            centreEscolar.setEsESO(cbEso.isChecked());
            centreEscolar.setEsPrimaria(cbPrimaria.isChecked());
            centreEscolar.setEsInfantil(cbInfantil.isChecked());

            afegeixEscolaAPI(centreEscolar);


            Intent intent = new Intent(this, CreateNewCenter.class);
            intent.putExtra(ERROR, error);
            intent.putExtra(SCHOOL, centreEscolar);
            intent.putExtra(ACTIVITY_ADD, "ComingFromActivityAdd");
            startActivity(intent);
        }

    }

    private void afegeixEscolaAPI(CentreEscolar centreEscolar){
        new ActivityAddCentre.AsyncRequest(this).execute();
    }

    private class AsyncRequest extends AsyncTask<String, Void, Integer> {

        private Context context;
        private ProgressDialog progressDialog;

        protected AsyncRequest(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {

            String[] auxType = new String[6];
            if(centreEscolar.isEsUni()){
                auxType[5] = "1";
            } else {
                auxType[5] = "0";
            }
            if(centreEscolar.isEsFP()){
                auxType[4] = "1";
            } else {
                auxType[4] = "0";
            }
            if(centreEscolar.isEsBatx()){
                auxType[3] = "1";
            } else {
                auxType[3] = "0";
            }
            if(centreEscolar.isEsESO()){
                auxType[2] = "1";
            } else {
                auxType[2] = "0";
            }
            if(centreEscolar.isEsPrimaria()){
                auxType[1] = "1";
            } else {
                auxType[1] = "0";
            }
            if(centreEscolar.isEsInfantil()){
                auxType[0] = "1";
            } else {
                auxType[0] = "0";
            }

            return schoolsRepo.addSchool(centreEscolar.getNomEscola(), centreEscolar.getAdresaEscola(), centreEscolar.getProvincia(), auxType, centreEscolar.getDescripcio());
        }

        @Override
        protected void onPostExecute(Integer done) {
            super.onPostExecute(done);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if(done == 0){
                Toast.makeText(this.context, getString(R.string.errorAdd),
                        Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this.context, getString(R.string.successfulInsertion),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
