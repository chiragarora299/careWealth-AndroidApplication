package com.example.carewellth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Object Button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.widget.Button button = (Button) findViewById(R.id.GetStartButton);
        LoadingDailog loadingDailog= new LoadingDailog(MainActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDailog.startLoadingDailog();
                Intent intent= new Intent(MainActivity.this,register_login.class) ;
                startActivity(intent);
                finish();
            }
        }
        );
        }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,Dashboard.class));
            finish();
        }
    }
}



