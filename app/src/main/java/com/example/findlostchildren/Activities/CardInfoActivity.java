package com.example.findlostchildren.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.findlostchildren.R;

public class CardInfoActivity extends AppCompatActivity {

    Button known , unknown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        known=findViewById(R.id.known_victim);
        unknown=findViewById(R.id.unknown_victim);

    }
}
