package com.example.findlostchildren.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findlostchildren.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.add_image_btn)
    ImageView addImageBtn;
    @BindView(R.id.arrow_right_imageView)
    ImageView arrowRightImageView;
    @BindView(R.id.edit_userName)
    EditText editUserName;
    @BindView(R.id.edit_phone_number)
    EditText editPhoneNumber;
    @BindView(R.id.button_reset_password)
    Button buttonResetPassword;
    @BindView(R.id.arrow_right_imageView2)
    ImageView arrowRightImageView2;
    @BindView(R.id.edit_city)
    EditText editCity;
    @BindView(R.id.edit_age)
    EditText editAge;
    @BindView(R.id.edit_facebook_link)
    EditText editFacebookLink;
    @BindView(R.id.edit_about)
    EditText editAbout;
    @BindView(R.id.done_imaageView)
    ImageView doneImaageView;

    @BindView(R.id.header_view1)
    View accountInformationView;
    @BindView(R.id.header_view2)
    View personalInformationView;

    Uri imageUri;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String userName , phone , city , age , facebookLink , about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        userName = editUserName.getText().toString();
        phone = editPhoneNumber.getText().toString().trim();
        city = editCity.getText().toString();
        age = editAge.getText().toString().trim();
        facebookLink = editFacebookLink.getText().toString();
        about = editAbout.getText().toString();


    }

    @OnClick({R.id.add_image_btn, R.id.arrow_right_imageView, R.id.button_reset_password, R.id.arrow_right_imageView2, R.id.done_imaageView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_image_btn:
                selectImage();
                break;
            case R.id.arrow_right_imageView:
                accountInformationView.setVisibility(View.VISIBLE);
                break;
            case R.id.button_reset_password:
                resetPassowrd();
                break;
            case R.id.arrow_right_imageView2:
                personalInformationView.setVisibility(View.VISIBLE);
                break;
            case R.id.done_imaageView:
                saveChange();
                break;
        }
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
                        .into(profileImage);

            }
        }
    }

    private void resetPassowrd() {
        final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        String email = "a@hmail.com"; //user.getEmail();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "Failed Sending Email" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveChange() {

        final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        if (imageUri != null){
            final String imageName = UUID.randomUUID().toString() + ".jpg";

            storageReference.child("Images").child("Users").child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.child("Images").child("Users").child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            databaseReference.child("Users").child("gzfUbSh1hBUJlNkFj5bM46SwRiG3").child("imageURL").setValue(imageURL);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        if (!userName.isEmpty()){
            databaseReference.child("Users").child("gzfUbSh1hBUJlNkFj5bM46SwRiG3").child("userName").setValue(userName);
        }

        if (!phone.isEmpty()){
            databaseReference.child("Users").child("gzfUbSh1hBUJlNkFj5bM46SwRiG3").child("phone").setValue(phone);
        }

        if (!city.isEmpty()){
            databaseReference.child("Users").child("gzfUbSh1hBUJlNkFj5bM46SwRiG3").child("city").setValue(city);
        }

        if (!city.isEmpty()){
            databaseReference.child("Users").child("gzfUbSh1hBUJlNkFj5bM46SwRiG3").child("city").setValue(city);
        }

        if (!age.isEmpty()){
            databaseReference.child("Users").child("gzfUbSh1hBUJlNkFj5bM46SwRiG3").child("age").setValue(age);
        }

        if (!facebookLink.isEmpty()){
            databaseReference.child("Users").child("gzfUbSh1hBUJlNkFj5bM46SwRiG3").child("facebookLink").setValue(facebookLink);
        }

        if (!about.isEmpty()){
            databaseReference.child("Users").child("gzfUbSh1hBUJlNkFj5bM46SwRiG3").child("about").setValue(about);
        }
        progressDialog.dismiss();




    }
}
