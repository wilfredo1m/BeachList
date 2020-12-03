package com.example.beachlist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// this is needed in order to run the program. if no main activity is set then nothing will run
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "FIREBASE LOGIN";
    private EditText emailEt, passwordEt;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    Button homeScreenButton,createAccountButton;

    //banned screen
    ConstraintLayout bannedLayout;
    Button testBtn, continueBtn;
    TextView remainingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this line makes it so the first page to be displayed is the one below.
        setContentView(R.layout.activity_main);

        bannedLayout = findViewById(R.id.banned_popup_layout);
        remainingTime = findViewById(R.id.remaining_time_tv);

        // Create Account Button
        createAccountButton = findViewById(R.id.btnCreateAccount);
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
        homeScreenButton = findViewById(R.id.btnLogin);
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Login User
                Login();
            }
        });

        testBtn= findViewById(R.id.test_popup_btn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayBannedScreen();
                disablePreviousScreenEntries();
            }
        });

        continueBtn= findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revertToPrevScreen();
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
                        assert user != null;
                        if (!user.isEmailVerified()) {
                            Toast.makeText(MainActivity.this, "Email Not Verified", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                            //to add this back later once page is fixed
                            openHomeScreen();
                        }
                    } else {
                        Log.e(TAG, "onFailure: Sign In Failed", task.getException().getCause());
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

    public void displayBannedScreen(){
        bannedLayout.setVisibility(View.VISIBLE);
        homeScreenButton.setClickable(false);
        createAccountButton.setClickable(false);

    }
    public void disablePreviousScreenEntries(){
        homeScreenButton.setVisibility(View.INVISIBLE);
        createAccountButton.setVisibility(View.INVISIBLE);

    }

    public void revertToPrevScreen(){
        homeScreenButton.setClickable(true);
        createAccountButton.setClickable(true);
        bannedLayout.setVisibility(View.INVISIBLE);
        homeScreenButton.setVisibility(View.VISIBLE);
        createAccountButton.setVisibility(View.VISIBLE);
    }

}