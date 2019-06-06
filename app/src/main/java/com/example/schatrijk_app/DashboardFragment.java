package com.example.schatrijk_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashboardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Dashboard");
        RegisterInteractionEvents();

        TextView txtMessage = getActivity().findViewById(R.id.txtmessage);

        if (false) {
            txtMessage.setText("Geen schatten in de buurt!");
            AppCompatImageButton btnStart = getActivity().findViewById(R.id.btnstart);
            btnStart.setVisibility(View.GONE);
        }
        else {
            txtMessage.setText("Er is een schat opgedoken!");
            AppCompatImageButton btnStart = getActivity().findViewById(R.id.btnstart);
            btnStart.setVisibility(View.VISIBLE);
        }
    }

    private void RegisterInteractionEvents() {
        AppCompatImageButton btnStart = getActivity().findViewById(R.id.btnstart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}