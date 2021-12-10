package com.example.whatsapp.Registr_Vxot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsapp.MainActivity;
import com.example.whatsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class VxotActivity extends AppCompatActivity {
    private Button bt_registr, bt_vxot;
    private EditText ed_email, ed_password, ed_name;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vxot);
        getSupportActionBar().hide();
        init();
    }

    private void init() {
        bt_vxot = findViewById(R.id.button_vxot);
        ed_email = findViewById(R.id.ed_email_vxot);
        ed_password = findViewById(R.id.ed_password_vxot);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    // при нажатие на текст "уже зарегистрирован "
    public void onClickText(View view) {
        Intent intent = new Intent(VxotActivity.this, RegistrActivity.class);
        startActivity(intent);
    }

    // при входе
    public void onClickVxot(View view) {
        String email = ed_email.getText().toString();
        String password = ed_password.getText().toString();
        // проверка
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Введи Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Веди Password", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Вхот в аккаунт");
            progressDialog.setMessage("Пожалуйста падаждите");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(VxotActivity.this, MainActivity.class);
                        startActivity(intent);

                        Toast.makeText(VxotActivity.this, "Вхот успешен", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(VxotActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}