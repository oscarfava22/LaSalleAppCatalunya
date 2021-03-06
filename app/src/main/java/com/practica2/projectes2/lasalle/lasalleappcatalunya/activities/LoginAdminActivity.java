package com.practica2.projectes2.lasalle.lasalleappcatalunya.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.impl.UsersDB;


public class LoginAdminActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private static final String ACTIVITY_ADD = "ActivitAdd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent intent = new Intent(LoginAdminActivity.this, CreateNewCenter.class);
                        intent.putExtra(ACTIVITY_ADD,"loginAdminActivity");
                        startActivity(intent);
                        LoginManager.getInstance().logOut(); //Tanquem la sessió per major seguretat.
                        Log.d("success", "Success");
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        String text = getString(R.string.cancel_error);
                        Toast.makeText(LoginAdminActivity.this, text, Toast.LENGTH_SHORT).show();
                        Log.d("cancel", "cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        String text = getString(R.string.login_dialog_error);
                        Toast.makeText(LoginAdminActivity.this, text, Toast.LENGTH_SHORT).show();
                        Log.d("error", "error");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onLogInClick (View view) {

        UsersDB usersDB = new UsersDB(this);

        EditText logInId = findViewById(R.id.login_id);
        EditText logInPassword = findViewById(R.id.login_password);

        String userId = logInId.getText().toString();
        String userPassword = logInPassword.getText().toString();

        if (usersDB.logInSuccessful(userId, userPassword) || usersDB.logInSuccessfulByEmail(userId, userPassword)) {

            logInId.setText(null);
            logInPassword.setText(null);
            Intent intent = new Intent(this, CreateNewCenter.class);
            startActivity(intent);

        } else {
            //No existeix l'usuari o admin
            String text = getString(R.string.login_error);
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        }
    }

    public void onRegisterClick (View view) {

        Intent intent = new Intent(this, RegisterNewAdmin.class);
        startActivity(intent);
    }
}
