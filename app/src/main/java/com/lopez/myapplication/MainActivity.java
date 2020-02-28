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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login, register;

    public FirebaseAuth firebaseAuth;
    public DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INITS
        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        login = (Button) findViewById(R.id.loginButton);
        register = (Button) findViewById(R.id.registerLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference().child("Parents");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameTxt = username.getText().toString();
                String passwordTxt = password.getText().toString();

                if (TextUtils.isEmpty(usernameTxt)) {
                    username.setError("Username should not be empty!");
                    return;
                }

                if (TextUtils.isEmpty(passwordTxt)) {
                    password.setError("Password should not be empty!");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(usernameTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // GET CHILD DATA
                            Query query = dr.orderByChild("email").equalTo(usernameTxt);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String childId = "";
                                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                            Parents parent = snap.getValue(Parents.class);
                                            childId = parent.getChildId();
                                        }
                                        Intent intent = new Intent(MainActivity.this, Home.class);
                                        intent.putExtra("CHILDID", childId);

                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login failed for user " + usernameTxt + ".", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Toast.makeText(MainActivity.this, "Login failed for user " + usernameTxt + ".", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
                finish();
            }
        });
    }
}
