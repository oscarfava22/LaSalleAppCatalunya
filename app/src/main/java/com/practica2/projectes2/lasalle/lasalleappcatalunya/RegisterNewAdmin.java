package com.practica2.projectes2.lasalle.lasalleappcatalunya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.User;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.impl.UsersDB;

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

        boolean error = false;

        if(!email.getText().toString().contains("@") || email.getText().toString().equals("") || emailIncorrecte(email.getText().toString())){

            email.setError(getString(R.string.errorEmail));
            error = true;
        }

        if(username.getText().toString().equals("")){

            username.setError(getString(R.string.errorUsername));
            error = true;
        }


        if(viewName.getText().toString().equals("")){

            viewName.setError(getString(R.string.errorName));
            error = true;

        }

        if(viewSurname.getText().toString().equals("")){

            viewSurname.setError(getString(R.string.errorSurname));
            error = true;
        }

        if(viewPass.getText().toString().isEmpty()){

            viewPass.setError(getString(R.string.errorPass));
            error = true;
        }

        if(!confirmPass.getText().toString().equals(viewPass.getText().toString())){

            confirmPass.setError(getString(R.string.errorConfirmPass));
            error = true;
        }

        if (!error) {
            UsersDB usersDB = new UsersDB(this);
            String userUsername = username.getText().toString();

            if(usersDB.existsUser(userUsername)) {
                //Error, ja existeix aquest usuari.
                String text = getString(R.string.register_error);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

            } else {

                String userName = viewName.getText().toString();
                String userSurname = viewSurname.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = viewPass.getText().toString();
                User user = new User(userName, userSurname, userUsername, userEmail, userPassword);
                usersDB.addUser(user);

                Intent intent = new Intent(this, CreateNewCenter.class);
                startActivity(intent);
            }
        }
    }

    private boolean emailIncorrecte(String email) {
        int pos, cont = 0;

        pos = email.indexOf('@');
        while (pos != -1){

            cont++;
            pos = email.indexOf('@', pos + 1);
        }

        if (cont > 1) return true;
        return false;
    }
}
