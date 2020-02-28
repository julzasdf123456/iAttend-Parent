package com.lopez.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText username, password, name, childId;
    Button login, register;

    public FirebaseAuth firebaseAuth;
    public DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // INITS
        username = (EditText) findViewById(R.id.emailRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        name = (EditText) findViewById(R.id.fullname);
        childId = (EditText) findViewById(R.id.childId);
        login = (Button) findViewById(R.id.loginRegister);
        register = (Button) findViewById(R.id.register);
        firebaseAuth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference().child("Parents");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, MainActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordTxt = password.getText().toString();
                String fullnameTxt = name.getText().toString();
                String usernameTxt = username.getText().toString();
                String childIdTxt = childId.getText().toString();

                if (TextUtils.isEmpty(fullnameTxt)) {
                    name.setError("Full name is required.");
                    return;
                }

                if (TextUtils.isEmpty(usernameTxt)) {
                    username.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(childIdTxt)) {
                    username.setError("Child ID is required.");
                    return;
                }

                if (TextUtils.isEmpty(passwordTxt)) {
                    password.setError("Password is required.");
                    return;
                } else {
                    if (passwordTxt.length() < 8) {
                        password.setError("Password length must be greater than 8.");
                        return;
                    }
                }

                dr.push().setValue(new Parents(fullnameTxt, childIdTxt, usernameTxt, passwordTxt));

                firebaseAuth.createUserWithEmailAndPassword(usernameTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Registration Successful. Welcome to iAttendance!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, Home.class));
                        } else {
                            Toast.makeText(Register.this, "Error creating user!", Toast.LENGTH_SHORT).show();
                            Log.e("ERR", task.getException().getMessage());
                        }
                    }
                });
            }
        });
    }
}
