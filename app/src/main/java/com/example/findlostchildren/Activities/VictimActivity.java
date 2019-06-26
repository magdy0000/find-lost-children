package com.example.findlostchildren.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findlostchildren.Models.VictimModel;
import com.example.findlostchildren.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VictimActivity extends AppCompatActivity {

    @BindView(R.id.victim_id_tv)
    TextView victimIdTv;
    String victimId ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference() ;
    FirebaseAuth auth = FirebaseAuth.getInstance() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim);
        ButterKnife.bind(this);
        victimIdTv = findViewById(R.id.victim_id_tv);
         victimId = getIntent().getExtras().getString("ID");
        victimIdTv.setText(victimId);

        getData();
    }


    private void getData() {


        ref.child("Victims").child(auth.getCurrentUser().getUid()).child(victimId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                VictimModel victimModel = dataSnapshot.getValue(VictimModel.class);

                Toast.makeText(VictimActivity.this, victimModel.getCity()+"", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
