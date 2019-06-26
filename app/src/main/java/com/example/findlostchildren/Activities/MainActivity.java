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
import android.widget.Toast;

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
    private Fragment fragment  ;
    private long backPressedTime ;

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
                        fragment = new HomeFragment();
                        loadFragment(fragment ,"home" );

                        getSupportActionBar().setTitle("Home");
                        break;
                    case R.id.action_myVictims:
                        fragment = new PersonalVictimFragment();
                        loadFragment(fragment ,"My Victims" );

                        getSupportActionBar().setTitle("My Victims");
                        break;
                    case R.id.action_search:
                        fragment = new SearchFragment();
                        loadFragment(fragment , "search");
                        getSupportActionBar().setTitle("Search");
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment,"profile");
                        getSupportActionBar().setTitle("Profile");
                        break;
                    case R.id.action_notification:
                        fragment = new NotificationFragment();

                        loadFragment(fragment,"notification");
                        getSupportActionBar().setTitle("Notification");
                        break;
                }
                return true;
            }
        });
    }

    private void loadStartFragment() {
        loadFragment(new HomeFragment(),"home");
    }

    private void loadFragment(Fragment fragment , String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment ,tag );
        fragmentTransaction.addToBackStack(null);

        getSupportFragmentManager().popBackStack();
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (!fragment.getTag().equals("home")) {
            fragment = new HomeFragment();

            loadFragment(fragment,"home");
        }else {


            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                finishAffinity();
            } else {
                Toast.makeText(this, "press again to exit ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
