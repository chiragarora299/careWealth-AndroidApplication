package com.example.carewellth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLogin;
    private ImageButton imgbackbtn;
    FirebaseAuth fAuth;
    TextView textViewtoR;
    TextView forgotPass;
    private CheckBox remMe;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imgbackbtn=findViewById(R.id.imgbackbtn2);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        fAuth = FirebaseAuth.getInstance();
        mLogin = findViewById(R.id.Login);
        textViewtoR= findViewById(R.id.textView4);
        forgotPass=findViewById(R.id.txtFrgPass);
        remMe=findViewById(R.id.remMe);
        LoadingDailog loadingDailog= new LoadingDailog(login.this);
//        sharedPrefrences
        sharedPreferences=getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        String mail=sharedPreferences.getString("email","");
        String passwords=sharedPreferences.getString("passowrd","");
        mEmail.setText(mail);
        mPassword.setText(passwords);
//        sharedPrefrence End;



        imgbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(login.this,register_login.class);
                startActivity(intent);
                finish();
            }
        });
        textViewtoR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(login.this,registration.class);
                startActivity(intent);
                finish();
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDailog.startLoadingDailog();
                if (remMe.isChecked()){
                    editor.putString("email",mEmail.getText().toString());
                    editor.putString("passowrd",mPassword.getText().toString());
                    editor.commit();

                }else{
                    editor.putString("email","");
                    editor.putString("passowrd","");
                    editor.commit();
                    
                }
                String Email = mEmail.getText().toString().trim();
                String Password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(Email)||TextUtils.isEmpty(Password)) {
                    mEmail.setError("Email or Password is Required");
                    loadingDailog.dismissDailog();

                    return;
                }
//                if (TextUtils.isEmpty(Password)) {
//                    mPassword.setError("Password is Required");
//                    return;
//                }
                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        loadingDailog.startLoadingDailog();
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), selftest.class));
                            finish();
                        }
                        else{
                            Toast.makeText(login.this, "Incorrect Email Or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your Email to receive Reset Link!!");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        extract the email and send reset link
                          String mail = resetMail.getText().toString();
                          fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void unused) {
                                  Toast.makeText(login.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();

                              }
                          }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull @NotNull Exception e) {
                                  Toast.makeText(login.this, "Error!!! Reset Link Not Sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          });


                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        close the dialog

                    }
                });
                passwordResetDialog.create().show();
            }
        });
        

    }
}