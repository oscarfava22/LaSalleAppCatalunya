package com.practica2.projectes2.lasalle.lasalleappcatalunya;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ActivityAddCentre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_centre);

        final Toolbar toolbar = findViewById(R.id.toolbar_activity_add_centre);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_add_new_centre));

    }
}
