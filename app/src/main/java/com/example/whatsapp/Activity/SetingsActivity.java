package com.example.whatsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.Users.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SetingsActivity extends AppCompatActivity {
    private ImageView image_soxron_profil, profile_image;
    private EditText ed_name_user, ed_status_user;
    private Button bt_save;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings);
        getSupportActionBar().hide();
        inite();


        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name_user.getText().toString();
                String status = ed_status_user.getText().toString();

                if (!name.equals("")) {

                    HashMap<String, Object> odj = new HashMap<>();
                    odj.put("username", name);
                    odj.put("status", status);

                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                            .updateChildren(odj);


                    Toast.makeText(SetingsActivity.this, "Успешно изменено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SetingsActivity.this, "Введи данные ", Toast.LENGTH_SHORT).show();
                }

            }
        });


        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);
                        Picasso.get()
                                .load(users.getProfileps())
                                .placeholder(R.drawable.profile)
                                .into(profile_image);

                        ed_status_user.setText(users.getStatus());
                        ed_name_user.setText(users.getUsername());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        image_soxron_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 50);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            Uri sFire = data.getData();
            profile_image.setImageURI(sFire);
            final StorageReference reference = storage.getReference().child("profile_pic")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(sFire).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileps").setValue(uri.toString());
                        }
                    });
                }
            });
        }
    }

    private void inite() {
        image_soxron_profil = findViewById(R.id.image_soxron_profil);
        profile_image = findViewById(R.id.profile_image);
        ed_name_user = findViewById(R.id.ed_name_user);
        ed_status_user = findViewById(R.id.ed_status_user);
        bt_save = findViewById(R.id.bt_save);

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public void ocCliceNazad(View view) {
        Intent intent = new Intent(SetingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}