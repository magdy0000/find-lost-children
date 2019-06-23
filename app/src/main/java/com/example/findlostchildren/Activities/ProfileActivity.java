package com.example.findlostchildren.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findlostchildren.Models.UsersModel;
import com.example.findlostchildren.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {

    ImageView profile_imageView;
    TextView name_textView;
    TextView about_textView;
    TextView city_textView;
    TextView phone_textView;
    Button edit_button;
    ImageView add_victim_button;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase database;
    DatabaseReference reference;

    UsersModel user;

    String userName;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        profile_imageView = findViewById(R.id.profile_imageView);
        name_textView = findViewById(R.id.name_textView);
        about_textView = findViewById(R.id.about_textView);
        city_textView = findViewById(R.id.city_textView);
        phone_textView = findViewById(R.id.phone_textView);
        edit_button = findViewById(R.id.edit_button);
        add_victim_button = findViewById(R.id.add_victim_button);

        //Current User
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //get Current User ID
        String userID = "gzfUbSh1hBUJlNkFj5bM46SwRiG3";//firebaseUser.getUid();


        //addListenerForSingleValueEvent to get data to Current User only
        reference.child("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //returned Data and save it into model named user

                userName = dataSnapshot.child("userName").getValue(String.class);
                phone = dataSnapshot.child("phone").getValue(String.class);

                name_textView.setText(userName);
                phone_textView.setText(phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        add_victim_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this , AddVictimActivity.class);
                startActivity(intent);
            }
        });


    }
}
