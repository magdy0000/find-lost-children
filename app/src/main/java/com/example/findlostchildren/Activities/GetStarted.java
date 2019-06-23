package com.example.findlostchildren.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.findlostchildren.R;

public class GetStarted extends AppCompatActivity {
     Button start ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        start = findViewById(R.id.getStart);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStarted.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
