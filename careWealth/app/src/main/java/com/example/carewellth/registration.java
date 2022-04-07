package com.example.carewellth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Object Button;
    EditText mName,mEmail,mPassword,mPhoneNumber;
    Spinner spinner;
    private TextView textViewtoL;
    Button mRegister;
    private ImageButton imgbackbtn;
    FirebaseAuth fAuth;
    DatabaseReference reference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        imgbackbtn=findViewById(R.id.imgbackbtn);
        textViewtoL=findViewById(R.id.textView4);
        mName = findViewById(R.id.Name);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mPhoneNumber = findViewById(R.id.PhoneNumber);
        mRegister = findViewById(R.id.Register);
        LoadingDailog loadingDailog= new LoadingDailog(registration.this);
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance("https://carewellth-dff40-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

//        code for spinner list of proctor
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.proctors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);










        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDailog.startLoadingDailog();
                String PhoneNo = mPhoneNumber.getText().toString().trim();
                String Email = mEmail.getText().toString().trim();
                String Password = mPassword.getText().toString().trim();
                String Name = mName.getText().toString();
                String proctor = spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(Email)) {
                    mEmail.setError("Email is Required");

                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    mPassword.setError("Password Required");
                    return;
                }
                if (!(PhoneNo.length()==10)){
                    mPhoneNumber.setError("Enter the valid Phone Number");
                    loadingDailog.dismissDailog();
                    return;
                }
                if (!(isValidPassword(Password) && Password.length() >= 8)) {
                    mPassword.setError("Password should contain UpperCase letter,Number And a Special character ");
                    loadingDailog.dismissDailog();
                    return;

                } else {
//                    Task<AuthResult> authResultTask = fAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
//                                finish();
//                            } else {
//                                Toast.makeText(registration.this, "error:Register UnSuccesfull", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        }
//                    });
                    registerUser(proctor,Name,Email,Password,PhoneNo);

                }
            }
            });




        imgbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(registration.this,register_login.class);
                startActivity(intent);
                finish();
            }
        });
        textViewtoL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(registration.this,login.class);
                startActivity(intent);
                finish();
            }
        });



    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void registerUser(String proctor, String name, String email, String password, String phoneno) {
        fAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                HashMap<String , Object> map = new HashMap<>();
                map.put("name" , name);
                map.put("email", email);
                map.put("proctor" , proctor);
                map.put("id" , fAuth.getCurrentUser().getUid());
                map.put("phoneno",phoneno);
                map.put("bio" , "");
                map.put("imageurl" , "default");

                reference.child("Users").child(fAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(registration.this, "Update the profile " +
                                    "for better expereince", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(registration.this , selftest.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public boolean isValidPassword( String Password) {

        Pattern pattern;
        Matcher matcher;

        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(Password);

        return matcher.matches();

    }
}