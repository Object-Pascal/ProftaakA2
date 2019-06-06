package com.example.schatrijk_app;

import android.Manifest;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

import com.example.schatrijk_app.Data.Compass;
import com.example.schatrijk_app.Data.Quest;
import com.example.schatrijk_app.Data.RiddleQuest;
import com.example.schatrijk_app.Data.TreasureLocations;
import com.example.schatrijk_app.Systems.CompassSystem;

import static android.content.Context.SENSOR_SERVICE;

public class TreasureHuntFragment extends Fragment {
    private CompassSystem compassSystem;
    private Quest currentQuest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.treasure_hunt_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Schatzoeken");

        TextView txtRiddle = getActivity().findViewById(R.id.txtriddle);
        TextView txtDistance = getActivity().findViewById(R.id.txtdistance);
        AppCompatImageButton btnQr = getActivity().findViewById(R.id.btnqr);
        ImageView imgPhoto = getActivity().findViewById(R.id.imgphoto);
        ImageView imgCompass = getActivity().findViewById(R.id.imgcompass);
        ImageView imgArrow = getActivity().findViewById(R.id.imgarrow);

        boolean riddleStage = false;
        boolean compassStage = false;
        boolean qrCodeStage = false;

        try {
            riddleStage = getArguments().getBoolean("riddle_state");
            compassStage = getArguments().getBoolean("compass_state");
            qrCodeStage = getArguments().getBoolean("qr_state");

            this.currentQuest = (Quest)getArguments().getSerializable("quest_object");
        }
        catch (NullPointerException e) {
            Toast.makeText(getContext(), "Er is een probleem opgetreden.", Toast.LENGTH_SHORT);
        }

        if (riddleStage) {
            txtRiddle.setVisibility(View.VISIBLE);
            txtDistance.setVisibility(View.GONE);
            btnQr.setVisibility(View.GONE);
            imgPhoto.setVisibility(View.GONE);
            imgCompass.setVisibility(View.GONE);
            imgArrow.setVisibility(View.GONE);

            txtRiddle.setText(getArguments().getString("riddle_text"));
        }
        else if (compassStage) {
            txtRiddle.setVisibility(View.GONE);
            txtDistance.setVisibility(View.VISIBLE);
            btnQr.setVisibility(View.GONE);
            imgPhoto.setVisibility(View.VISIBLE);
            imgCompass.setVisibility(View.VISIBLE);
            imgArrow.setVisibility(View.VISIBLE);

            RegisterCompass();
        }
        else if (qrCodeStage) {
            txtRiddle.setVisibility(View.GONE);
            txtDistance.setVisibility(View.GONE);
            btnQr.setVisibility(View.VISIBLE);
            imgPhoto.setVisibility(View.VISIBLE);
            imgCompass.setVisibility(View.GONE);
            imgArrow.setVisibility(View.GONE);

            RegisterQrEvents();
        }
        else {
            txtRiddle.setText("Geen zoektocht gestart!");
        }
    }

    private void RegisterCompass() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
        compassSystem = new CompassSystem(new Compass(getActivity().getApplicationContext(), TreasureLocations.getTreasureLocations()[0].getCenterLocation()), (SensorManager)getActivity().getSystemService(SENSOR_SERVICE), this);
        compassSystem.start();
    }

    public void onSensorChanged(int resultAngle, int compassAngle, float distance) {
        ImageView imgArrow = getActivity().findViewById(R.id.imgarrow);
        ImageView imgCompass = getActivity().findViewById(R.id.imgcompass);
        TextView txtDistance = getActivity().findViewById(R.id.txtdistance);

        imgArrow.setRotation(resultAngle);
        imgCompass.setRotation(-compassAngle);

        txtDistance.setText("Afstand: " + distance + "m");

        if (this.currentQuest.verify(compassSystem.getPhoneLocation())) {
            // TODO: Zoektocht afsluiten en kortingen toedienen
            Toast.makeText(getContext(), "Schat gevonden", Toast.LENGTH_SHORT);
        }
    }

    private void RegisterQrEvents() {
        AppCompatImageButton btnQr = getActivity().findViewById(R.id.btnqr);
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}