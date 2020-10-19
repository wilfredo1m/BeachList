package com.example.beachlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Scanner;

// this is needed in order to run the program. if no main activity is set then nothing will run
public class MainActivity extends AppCompatActivity {
    private Button createAccountButton, homeScreenButton;
    private EditText emailEt, passwordEt;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this line makes it so the first page to be displayed is the one below.
        setContentView(R.layout.activity_main);

        // Create Account Button
        createAccountButton = (Button) findViewById(R.id.btnCreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateAccountScreen();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.etEmail);
        passwordEt = findViewById(R.id.etPassword);
        progressDialog = new ProgressDialog(this);

        // Login button
        homeScreenButton = (Button) findViewById(R.id.btnLogin);
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Login User
                Login();
            }
        });

    }

    public void Login() {
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        // Check if email or password field left blank
        if((email.isEmpty()) || (password.isEmpty())){
            displayEmptyFieldError();
        }
        else {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user.isEmailVerified() == false) {
                            Toast.makeText(MainActivity.this, "Email Not Verified", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                            //to add this back later once page is fixed
                            openHomeScreen();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Log In Failed!", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }

    // Displays when Email and/or Password field are left blank
    public void displayEmptyFieldError(){
        Toast.makeText(MainActivity.this, "Enter Email and Password",Toast.LENGTH_SHORT).show();
    }

    // Opens Create Account Screen
    public void openCreateAccountScreen(){
        //Intent openScreen = new Intent(this, ActiveListings.class);
        Intent openScreen = new Intent(this, CreateAccount.class);
        startActivity(openScreen);
    }

    // Opens Home Screen
    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }
}