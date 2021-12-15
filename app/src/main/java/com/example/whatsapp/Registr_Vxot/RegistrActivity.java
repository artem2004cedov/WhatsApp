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
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.Activity.MainActivity;
import com.example.whatsapp.R;
import com.example.whatsapp.Users.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrActivity extends AppCompatActivity {
    private Button bt_registr, bt_vxot;
    private EditText ed_email, ed_password, ed_name;
    private TextView text_smena;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr__main);
        getSupportActionBar().hide();
        init();
    }

    private void init() {
        bt_registr = findViewById(R.id.button_registation);
        bt_vxot = findViewById(R.id.button_vxot);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        ed_name = findViewById(R.id.ed_name);
        text_smena = findViewById(R.id.tv_akk_est);
        progressDialog = new ProgressDialog(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

    }


    //при регистрации
    public void onClickRegistr(View view) {
        String email = ed_email.getText().toString();
        String password = ed_password.getText().toString();
        String name = ed_name.getText().toString();
        // проверка
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Введи Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Веди Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Веди Name", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Создание аккаунта");
            progressDialog.setMessage("Пожалуйста падаждите");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Users users = new Users(name, email, password);
                        String id = task.getResult().getUser().getUid();
                        // содамет базу с названием Users, передает id пользователя и веденые данные
                        database.getReference().child("Users").child(id).setValue(users);
                        progressDialog.dismiss();
                        Intent intent = new Intent(RegistrActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegistrActivity.this, "Аккаунт успешно зарегистрирован", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // при нажатие на текст "уже зарегистрирован "
    public void onClickText(View view) {
        Intent intent = new Intent(RegistrActivity.this, VxotActivity.class);
        startActivity(intent);
    }

    // от кнопки входа   от гугла
    public void onClickGoogle(View view) {
        signIn();
    }

    //    _____________________________________ методы от гугла, для входа в акк____________________
    int RC_SIGN_IN = 65;
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Users users = new Users();
                            users.setUserid(user.getUid());
                            users.setUsername(user.getDisplayName());
                            users.setProfileps(user.getPhotoUrl().toString());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);

                            Intent intent = new Intent(RegistrActivity.this, MainActivity.class);
                            startActivity(intent);

                            Toast.makeText(RegistrActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistrActivity.this, "Произашла ошибка ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    ____________________________________________________________________________________________

}