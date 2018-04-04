package com.practica2.projectes2.lasalle.lasalleappcatalunya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterNewAdmin extends AppCompatActivity {

    private EditText email;
    private EditText username;
    private EditText viewName;
    private EditText confirmPass;
    private EditText viewPass;
    private EditText viewSurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_new_admin_activity);

        email = findViewById(R.id.correu);
        username = findViewById(R.id.nomUsuari);
        viewName = findViewById(R.id.nomReal);
        viewSurname = findViewById(R.id.CognomReal);
        viewPass = findViewById(R.id.contrassenya);
        confirmPass = findViewById(R.id.confirmContrassenya);

    }

    /**
     * Cuando el usuario quiere registrarse, se comprueban las dadas
     * @param view componente registrado
     */
    public void onClickButtonRegister(View view){

        if(! email.getText().toString().contains("@")){

            email.setError(getString(R.string.errorEmail));
        }

        if(username.getText().toString().equals("")){

            username.setError(getString(R.string.errorUsername));
        }


        if(viewName.getText().toString().equals("")){

            viewName.setError(getString(R.string.errorName));

        }

        if(viewSurname.getText().toString().equals("")){

            viewSurname.setError(getString(R.string.errorSurname));
        }

        if(viewPass.getText().toString().equals("")){

            viewPass.setError(getString(R.string.errorPass));
        }

        if(confirmPass.getText().toString().equals(viewPass.getText().toString())){

            confirmPass.setError(getString(R.string.errorConfirmPass));
        }
    }
}
