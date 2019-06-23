package com.example.findlostchildren.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.findlostchildren.Helpers.InputValidator;
import com.example.findlostchildren.Models.VictimModel;
import com.example.findlostchildren.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddVictimActivity extends AppCompatActivity {

    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.victim_image)
    CircleImageView victimImage;
    @BindView(R.id.add_image_btn)
    ImageView addImageBtn;
    @BindView(R.id.add_image_btn_layout)
    LinearLayout addImageBtnLayout;
    @BindView(R.id.victim_name_et)
    EditText victimNameEt;
    @BindView(R.id.victim_city_et)
    EditText victimCityEt;
    @BindView(R.id.victim_age_et)
    EditText victimAgeEt;
    @BindView(R.id.victim_number_et)
    EditText victimNumberEt;
    @BindView(R.id.victim_description_et)
    EditText victimDescriptionEt;
    @BindView(R.id.add_victim_btn)
    ImageView addVictimBtn;

    private Uri imageUri;
    private ArrayList<String> imagesUrl;
    private String name, city, age, number, description;

    //Firebase Database
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    //Firebase Storage
    private FirebaseStorage storage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_victim);
        ButterKnife.bind(this);
        imagesUrl = new ArrayList<>();
    }

    @OnClick({R.id.back_btn, R.id.victim_image, R.id.add_image_btn, R.id.add_victim_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                break;
            case R.id.victim_image:
                selectImage();
                break;
            case R.id.add_image_btn:
                addImage();
                break;
            case R.id.add_victim_btn:
                if (getInputData())
                    saveVictim();
                break;
        }
    }

    private void saveVictim() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Adding Victim");
        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Victims");
        VictimModel newVictim = new VictimModel(imagesUrl, name, city, age, number, description);
        databaseReference.push().setValue(newVictim).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(AddVictimActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });

    }

    private boolean getInputData() {
        if (imagesUrl.isEmpty()) {
            Toast.makeText(this, "Please Select Victim Photo", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!InputValidator.victimValidation(getApplicationContext(), victimNameEt, victimCityEt, victimAgeEt, victimNumberEt, victimDescriptionEt))
            return false;

        name = victimNameEt.getText().toString();
        city = victimCityEt.getText().toString();
        age = victimAgeEt.getText().toString().trim();
        number = victimNumberEt.getText().toString().trim();
        description = victimDescriptionEt.getText().toString();

        return true;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

                Picasso.with(getApplicationContext())
                        .load(imageUri)
                        .into(victimImage);

            }
        }
    }

    private void addImage() {
        if (imageUri == null)
            Toast.makeText(this, "Please Select Photo", Toast.LENGTH_SHORT).show();
        else {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Image Uploading");
            progressDialog.setMessage("Please Wait.....");
            progressDialog.show();

            final String imageName = UUID.randomUUID().toString() + ".jpg";
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference().child("Images").child("Victims");

            storageReference.child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressDialog.dismiss();
                            String imageURL = uri.toString();
                            imagesUrl.add(imageURL);
                            imageUri = null;
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddVictimActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }

    }
}
