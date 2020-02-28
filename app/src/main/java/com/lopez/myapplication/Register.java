package com.lopez.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    EditText username, password, name, childId;
    Button login, register;

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, MainActivity.class));
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
                    username.setError("Email is required.");
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
            }
        });
    }
}
