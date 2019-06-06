package com.example.schatrijk_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.Button;

public class StartupSchermActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_screen_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        RegisterInteractionEvents();
    }

    private void RegisterInteractionEvents() {
        AppCompatImageButton btnParticipate = findViewById(R.id.btnparticipate);
        btnParticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // TODO: Check if the sensors exist and are in working order
            if (true) {
                Intent i = new Intent(getApplicationContext(), new DrawerActivity().getClass());
                startActivity(i);
            }
            }
        });
    }
}