package com.example.findlostchildren.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;import android.view.View;
import android.support.v7.widget.Toolbar;

import com.example.findlostchildren.Fragments.HomeFragment;
import com.example.findlostchildren.Fragments.NotificationFragment;
import com.example.findlostchildren.Fragments.PersonalVictimFragment;
import com.example.findlostchildren.Fragments.ProfileFragment;
import com.example.findlostchildren.Fragments.SearchFragment;
import com.example.findlostchildren.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setTitle("Home");
        loadStartFragment();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        loadFragment(new HomeFragment());
                        getSupportActionBar().setTitle("Home");
                        break;
                    case R.id.action_myVictims:
                        loadFragment(new PersonalVictimFragment());
                        getSupportActionBar().setTitle("My Victims");
                        break;
                    case R.id.action_search:
                        loadFragment(new SearchFragment());
                        getSupportActionBar().setTitle("Search");
                        break;
                    case R.id.action_profile:
                        loadFragment(new ProfileFragment());
                        getSupportActionBar().setTitle("Profile");
                        break;
                    case R.id.action_notification:
                        loadFragment(new NotificationFragment());
                        getSupportActionBar().setTitle("Notification");
                        break;
                }
                return true;
            }
        });
    }

    private void loadStartFragment() {
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);

        getSupportFragmentManager().popBackStack();
        fragmentTransaction.commit();
    }


}
