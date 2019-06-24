package com.example.findlostchildren.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.findlostchildren.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VictimActivity extends AppCompatActivity {

    @BindView(R.id.victim_id_tv)
    TextView victimIdTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim);
        ButterKnife.bind(this);
        victimIdTv = findViewById(R.id.victim_id_tv);
        String victimId = getIntent().getExtras().getString("ID");
        victimIdTv.setText(victimId);
    }
}
