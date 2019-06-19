package com.example.schatrijk_app;

import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.schatrijk_app.Data.QuestLines;
import com.example.schatrijk_app.Data.RiddleQuest;
import com.example.schatrijk_app.Data.SystemStateData;
import com.example.schatrijk_app.Systems.QuestLine;

import java.io.Serializable;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {

    private Menu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawerMenu = null;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_dashboard);

        SystemStateData.drawerMenu = navigationView.getMenu();
        SystemStateData.drawerMenu.findItem(R.id.nav_treasure_hunt).setVisible(false);
        SystemStateData.drawerMenu.findItem(R.id.nav_stop_treasure_hunt).setVisible(false);
        SystemStateData.drawerMenu.findItem(R.id.nav_dashboard).setVisible(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_dashboard:
                fragment = new DashboardFragment();
                break;
            case R.id.nav_kortingen:
                fragment = new KortingenFragment();
                break;
            case R.id.nav_treasure_hunt:
                fragment = new TreasureHuntFragment();
                break;
            case R.id.nav_stop_treasure_hunt:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Weet je zeker dat je wilt stoppen met de zoektocht?")
                        .setPositiveButton("Ja", this)
                        .setNegativeButton("Nee", this)
                .show();
                break;
        }
        NavigateToFragment(fragment);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                if (SystemStateData.isTreasureHunting) {
                    SystemStateData.isTreasureHunting = false;
                    NavigateToFragment(new DashboardFragment());

                    SystemStateData.drawerMenu.findItem(R.id.nav_treasure_hunt).setVisible(false);
                    SystemStateData.drawerMenu.findItem(R.id.nav_stop_treasure_hunt).setVisible(false);
                    SystemStateData.drawerMenu.findItem(R.id.nav_dashboard).setVisible(true);
                }
                break;
        }
    }

    private void NavigateToFragment(Fragment fragment) {
        if (fragment != null) {
            Bundle fragmentBundle = new Bundle();
            QuestLine dummyQuestLine = QuestLines.getDefaultQuestLine();
            fragmentBundle.putString("riddle_text", ((RiddleQuest)dummyQuestLine.getCurrentQuest()).getRiddle());
            fragmentBundle.putSerializable("quest_object", dummyQuestLine.getCurrentQuest());

            if (dummyQuestLine.getCurrentQuest() instanceof RiddleQuest) {
                fragmentBundle.putBoolean("riddle_state", true);
                fragmentBundle.putBoolean("compass_state", false);
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
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}