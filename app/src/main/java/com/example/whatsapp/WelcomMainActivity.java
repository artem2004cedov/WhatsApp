package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsapp.Registr_Vxot.RegistrActivity;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomMainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        // если текущий пользователь
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(WelcomMainActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_main);
        getSupportActionBar().hide();

    }
    // переход на экран регистрации

    public void onClickStart(View view) {
        Intent intent = new Intent(WelcomMainActivity.this, RegistrActivity.class);
        startActivity(intent);
    }
}