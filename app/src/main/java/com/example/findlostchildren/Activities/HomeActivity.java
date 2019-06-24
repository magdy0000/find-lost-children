package com.example.findlostchildren.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findlostchildren.Constrollers.Adapters.VictimAdapter;
import com.example.findlostchildren.Models.VictimModel;
import com.example.findlostchildren.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView victimRecyclerView;
    private View loadingView;
    private VictimAdapter victimAdapter;
    private TextView noDataTV;

    private List<VictimModel> victimsArrayList = new ArrayList<>();

    //Firebase Database
    private FirebaseDatabase database;
    private DatabaseReference victimReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        victimRecyclerView = (RecyclerView) findViewById(R.id.victim_RecyclerView);
        loadingView = findViewById(R.id.victim_loading_view);
        noDataTV = findViewById(R.id.no_data_tv);

        victimRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

        getVictimsData();
    }

    private void getVictimsData() {
        database = FirebaseDatabase.getInstance();
        victimReference = database.getReference();


        victimReference.child("Victims").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                victimsArrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot childSnapShot: snapshot.getChildren()){
                        VictimModel victimModel = childSnapShot.getValue(VictimModel.class);
                        victimsArrayList.add(victimModel);
                        victimAdapter.notifyDataSetChanged();
                    }
                }
                Collections.reverse(victimsArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(victimsArrayList != null) {
            victimAdapter = new VictimAdapter(getApplicationContext(), victimsArrayList);
            victimRecyclerView.setAdapter(victimAdapter);
            loadingView.setVisibility(View.GONE);
        }

    }
}
