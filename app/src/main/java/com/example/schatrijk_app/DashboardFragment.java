package com.example.schatrijk_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schatrijk_app.Data.LocationBounds;
import com.example.schatrijk_app.Data.Quest;
import com.example.schatrijk_app.Data.QuestLines;
import com.example.schatrijk_app.Data.RiddleQuest;
import com.example.schatrijk_app.Systems.QuestLine;

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
        RegisterQuestFetcher();

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
            Fragment fragment = new TreasureHuntFragment();
            if (fragment != null) {
                // Dummy data
                Bundle fragmentBundle = new Bundle();
                QuestLine dummyQuestLine = QuestLines.getDefaultQuestLine();
                fragmentBundle.putString("riddle_text", ((RiddleQuest)dummyQuestLine.getCurrentQuest()).getRiddle());
                fragmentBundle.putSerializable("quest_object", dummyQuestLine.getCurrentQuest());

                if (dummyQuestLine.getCurrentQuest() instanceof RiddleQuest) {
                    fragmentBundle.putBoolean("riddle_state", false);
                    fragmentBundle.putBoolean("compass_state", true);
                    fragmentBundle.putBoolean("qr_state", false);
                }
                /*else if (dummyQuestLine.getCurrentQuest() instanceof CompassQuest) {
                    fragmentBundle.putBoolean("riddle_state", false);
                    fragmentBundle.putBoolean("compass_state", true);
                    fragmentBundle.putBoolean("qr_state", false);
                }
                else if (dummyQuestLine.getCurrentQuest() instanceof QrQuest) {
                    fragmentBundle.putBoolean("riddle_state", false);
                    fragmentBundle.putBoolean("compass_state", false);
                    fragmentBundle.putBoolean("qr_state", true);
                }*/

                fragment.setArguments(fragmentBundle);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.commit();
            }

            DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void RegisterQuestFetcher() {
        // TODO: Timed checker voor het binnenhalen van quests
    }
}