package com.example.carewellth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class register_login extends AppCompatActivity {
    Button Register;
    Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);
        Login = findViewById(R.id.Login);
        LoadingDailog loadingDailog= new LoadingDailog(register_login.this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDailog.startLoadingDailog();
                Intent intent= new Intent(register_login.this,login.class);
                startActivity(intent);

            }
        });
        Register = findViewById(R.id.Register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDailog.startLoadingDailog();
                Intent intent = new Intent(register_login.this, registration.class);
                startActivity(intent);
                finish();
            }
        }

        );
    }
}