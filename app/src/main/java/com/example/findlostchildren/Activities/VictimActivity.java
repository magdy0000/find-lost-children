package com.example.findlostchildren.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.findlostchildren.Models.VictimModel;
import com.example.findlostchildren.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VictimActivity extends AppCompatActivity {
    Button known , unknown;

    TextView victimIdTv;
    String victimId ;
    public static String userID;
    String id ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference() ;
    FirebaseAuth auth = FirebaseAuth.getInstance() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim);

        victimIdTv = findViewById(R.id.victim_id_tv);

         victimId = getIntent().getExtras().getString("ID");
         id = getIntent().getExtras().getString("userId");

        victimIdTv.setText(victimId);

        known=findViewById(R.id.known_victim);
        unknown=findViewById(R.id.unknown_victim);

        known.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                known.setEnabled(false);
                OneSignal.startInit(VictimActivity.this).init();

                final FirebaseUser user=auth.getCurrentUser();
                final String login_user = user.getUid();

                OneSignal.sendTag("User_ID",login_user);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            String send_email;

                            //This is a Simple Logic to Send Notification different Device Programmatically....
                            if (login_user.equals(user.getUid())) {

                                send_email = "gzfUbSh1hBUJlNkFj5bM46SwRiG3";

                            } else {
                                send_email = user.getUid();
                            }

                            try {
                                String jsonResponse;

                                URL url = new URL("https://onesignal.com/api/v1/notifications");
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setUseCaches(false);
                                con.setDoOutput(true);
                                con.setDoInput(true);

                                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                con.setRequestProperty("Authorization", "Basic NDRjYzUxMGEtMjBhYy00N2VhLWI4ZDctZWE1MTFhMTUwNGY4");
                                con.setRequestMethod("POST");

                                String strJsonBody = "{"
                                        + "\"app_id\": \"8093365a-3064-472d-b288-26cb8449fac3\","

                                        + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                        + "\"data\": {\"foo\": \"bar\"},"
                                        + "\"contents\": {\"en\": \"Somebody found your victim!\"}"
                                        + "}";


                                System.out.println("strJsonBody:\n" + strJsonBody);

                                byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                                con.setFixedLengthStreamingMode(sendBytes.length);

                                OutputStream outputStream = con.getOutputStream();
                                outputStream.write(sendBytes);

                                int httpResponse = con.getResponseCode();
                                System.out.println("httpResponse: " + httpResponse);

                                if (httpResponse >= HttpURLConnection.HTTP_OK
                                        && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                    Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                    scanner.close();
                                } else {
                                    Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                    scanner.close();
                                }
                                System.out.println("jsonResponse:\n" + jsonResponse);

                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    }
                });
            }


        });


        getData();
    }


    private void getData() {


        ref.child("Victims").child(id).child(victimId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                VictimModel victimModel = dataSnapshot.getValue(VictimModel.class);
                userID=victimModel.getUserId();
                Toast.makeText(VictimActivity.this, victimModel.getCity()+"", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
