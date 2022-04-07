package com.example.carewellth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;


public class Dashboard extends AppCompatActivity {
BottomNavigationView bnv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);




//        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,new homeFragment()).commit();



        bnv=findViewById(R.id.bottomNavigationView);

        bnv.setBackground(null);
        bnv = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId()) {

                    case R.id.home:
                        temp = new homeFragment();

                        break;
                    case R.id.parent:
                        temp = new parentFragment();
                        break;
                    case R.id.medical:
                        temp = new medicalFragment();
                        break;
                    case R.id.proctor:
                        temp = new proctorFragment();
                        break;
                    case R.id.user:
                        temp = new userFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, temp).commit();
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,new homeFragment()).commit();


        }

    }

