package com.example.whatsapp.Chats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.Adapter.CatheAdapter;
import com.example.whatsapp.Activity.MainActivity;
import com.example.whatsapp.R;
import com.example.whatsapp.Users.MassegeModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroopActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private ImageView image_user_bar, image_Esc;
    private RecyclerView сhate_reckle;
    private ImageView image_otpraviti;
    private TextView textView_name;
    private EditText ed_massege;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groop);

        getSupportActionBar().hide();
        image_user_bar = findViewById(R.id.image_user_bar);

        image_Esc = findViewById(R.id.image_Esc);
        сhate_reckle = findViewById(R.id.Chate_reckle);
        image_otpraviti = findViewById(R.id.image_otpraviti);
        ed_massege = findViewById(R.id.ed_massege);
        textView_name = findViewById(R.id.textView_name);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        image_Esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroopActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        textView_name.setText("Групповой чат ");
        //        установка адаптера в рецайкл вью
        final ArrayList<MassegeModel> massegeModels = new ArrayList<>();
//      id пользователя который в MainActivity
        final String senderId = FirebaseAuth.getInstance().getUid();

        final CatheAdapter catheAdapter = new CatheAdapter(massegeModels, this);
        сhate_reckle.setAdapter(catheAdapter);
//
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        сhate_reckle.setLayoutManager(linearLayoutManager);

        database.getReference().child("Group Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    massegeModels.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MassegeModel model = dataSnapshot.getValue(MassegeModel.class);
                        massegeModels.add(model);
                    }
                    catheAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        image_otpraviti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String massageed = ed_massege.getText().toString();
                if (!TextUtils.isEmpty(ed_massege.getText().toString())) {
                    final MassegeModel model = new MassegeModel(senderId, massageed);
                    model.setTimestamp(new Date().getTime());

                    ed_massege.setText("");
                    database.getReference().child("Group Chat")
                            .push()
                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(GroopActivity.this, "vfdhz", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
//


    }
}