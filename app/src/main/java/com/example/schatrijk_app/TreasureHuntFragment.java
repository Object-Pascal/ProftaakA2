package com.example.schatrijk_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TreasureHuntFragment extends Fragment {
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
        AppCompatImageButton btnQr = getActivity().findViewById(R.id.btnqr);
        ImageView imgPhoto = getActivity().findViewById(R.id.imgphoto);
        ImageView imgCompass = getActivity().findViewById(R.id.imgcompass);
        ImageView imgArrow = getActivity().findViewById(R.id.imgarrow);

        boolean riddleStage = false;
        boolean compassStage = false;
        boolean qrCodeStage = true;

        if (riddleStage) {
            txtRiddle.setVisibility(View.VISIBLE);
            btnQr.setVisibility(View.GONE);
            imgPhoto.setVisibility(View.GONE);
            imgCompass.setVisibility(View.GONE);
            imgArrow.setVisibility(View.GONE);

            txtRiddle.setText("<raadsel_plek>");
        }
        else if (compassStage) {
            txtRiddle.setVisibility(View.GONE);
            btnQr.setVisibility(View.GONE);
            imgPhoto.setVisibility(View.VISIBLE);
            imgCompass.setVisibility(View.VISIBLE);
            imgArrow.setVisibility(View.VISIBLE);

            RegisterCompassEvents();
        }
        else if (qrCodeStage) {
            txtRiddle.setVisibility(View.GONE);
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

    private void RegisterCompassEvents() {
        // TODO: Eventuele kompas event logic
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
