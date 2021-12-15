package com.example.whatsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsapp.Adapter.FragmentAdapter;
import com.example.whatsapp.Chats.GroopActivity;
import com.example.whatsapp.R;
import com.example.whatsapp.Registr_Vxot.RegistrActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inite();
    }

    private void inite() {
        tabLayout = findViewById(R.id.tableLayot);
        viewPager = findViewById(R.id.Viper_layout);
        auth = FirebaseAuth.getInstance();

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }


    // создаем меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // доступ к айтемам
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // создать групу
        if (item.getItemId() == R.id.menu_create_grupe) {
            Intent intent = new Intent(MainActivity.this, GroopActivity.class);
            startActivity(intent);
        }

        // настройки
        if (item.getItemId() == R.id.menu_setting) {
            Intent intent = new Intent(MainActivity.this, SetingsActivity.class);
            startActivity(intent);
        }
        // выходит
        if (item.getItemId() == R.id.menu_logaut) {
            auth.signOut();
            Intent intent = new Intent(MainActivity.this, RegistrActivity.class);
            startActivity(intent);
        }
        return true;
    }
}