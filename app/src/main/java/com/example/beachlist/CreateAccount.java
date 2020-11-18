package com.example.beachlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateAccount extends AppCompatActivity {
    private static final String TAG = "CustomAuthActivity";

    private EditText fNameEt, lNameEt, idNumberEt, emailEt, passwordEt, gradDateEt, phoneNumEt;
    private ImageView profilePicture;
    private Uri filePath;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private StorageReference storageReference;
    public static final int IMAGE_REQUEST = 33;
    public static final int PROCESSED_OK = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //instance of authentication
        mAuth = FirebaseAuth.getInstance();
        //instance of the database
        database = FirebaseDatabase.getInstance();

        profilePicture = findViewById(R.id.ivProfileImage);

        // Set Profile Picture Button
        Button profilePicButton = findViewById(R.id.btn_Profile_image);
        profilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Access the phones camera roll to let user pick their profile picture
                openCameraRoll();
            }
        });

        // Get input fields
        getUserInputs();

        // Create Account Button
        Button createAccountButton = findViewById(R.id.btnCreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Register the User
                firebaseUserRegister();
            }
        });

        // Cancel Button
        Button cancelButton = findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginScreen();
            }
        });
    }

    // Opens Camera Roll
    public void openCameraRoll() {
        Intent openCameraRoll = new Intent(Intent.ACTION_PICK);

        // Location of where to find the pictures
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        // URI of the path to pictures
        Uri data = Uri.parse(pictureDirectoryPath);

        // Set data and type (* means all image types)
        openCameraRoll.setDataAndType(data,"image/*");

        // start the activity (accessing camera roll)
        startActivityForResult(openCameraRoll, IMAGE_REQUEST);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == PROCESSED_OK) {
            // Accessed camera roll successfully
            if(requestCode == IMAGE_REQUEST) {
                // Camera roll sent back a picture

                // Address of image in phone
                filePath = data.getData();

                // Stream to read image data
                InputStream input;

                try {
                    input = getContentResolver().openInputStream(filePath);

                    // Get Bitmap from InputStream
                    Bitmap profileImage = BitmapFactory.decodeStream(input);

                    // Displays image in the application
                    profilePicture.setImageBitmap(profileImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void firebaseUserRegister() {
        // Check if any field left blank
        if(anyEmptyFields()) {
            displayEmptyFieldError();
            return;
        }

        final String password = passwordEt.getText().toString();
        final String email = emailEt.getText().toString();

        // Provided email cant be shorter than 19 characters since "@student.csulb.edu" is fixed at 18 characters
//        if (email.length() <= 18) {
//            displayMalformedEmailError();
//            return;
//        }
//        // Checks if the email provided is a student csulb email
//        else if (!isValidSchoolEmail(email)) {
//            displayMalformedEmailError();
//            return;
//        }

        //Login progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "signInWithCustomToken: success");
                    //Set firebase user to current user instance
                    user = mAuth.getCurrentUser();
                    createUserAccount(
                            fNameEt.getText().toString(),
                            lNameEt.getText().toString(),
                            idNumberEt.getText().toString(),
                            emailEt.getText().toString(),
                            gradDateEt.getText().toString(),
                            phoneNumEt.getText().toString()
                            );
                    Toast.makeText(CreateAccount.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    sendValidationEmail(user);
                } else {
                    Log.w(TAG, "signInWithCustomToken: failure", task.getException());
                    Toast.makeText(CreateAccount.this, "Sign Up Failed",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    public void createUserAccount(String fname, String lname, String idnumber, String email, String graddate, String phonenum) {
        final UserData currentUser = new UserData();

        currentUser.setFirstName(fname);
        currentUser.setLastName(lname);
        currentUser.setIdNumber(idnumber);
        currentUser.setEmail(email);
        currentUser.setGradDate(graddate);
        currentUser.setPhoneNum(phonenum);

        //Set authenticated user ID
        final String userId = user.getUid();
        currentUser.setUserId(userId);
        //testing github webhook hehehe

        //Initialize database within User reference with a child using user ID
        final DatabaseReference userReference = database.getReference("users").child(userId).child("data");
        //Write data to database
        userReference.setValue(currentUser);

        //Initialize database within User reference with a child using user ID
        final DatabaseReference adminReference = database.getReference("users").child(userId).child("isAdmin");
        //write data to database
        adminReference.setValue(false);

        //If user has selected an image
        if(filePath != null) {
            //Store user image
            storageReference = FirebaseStorage.getInstance().getReference();
            final StorageReference imageRef = storageReference.child("images/" + filePath.getLastPathSegment());
            UploadTask uploadTask = imageRef.putFile(filePath);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "signInWithCustomToken: failure", e.getCause());
                    Toast.makeText(CreateAccount.this, "Failed to Store Image", Toast.LENGTH_SHORT).show();
                    logout();
                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "uploadImageWithCustomToken: success");
                    Task<Uri> downloadUrl = imageRef.getDownloadUrl();
                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageReference = uri.toString();
                            userReference.child("imageUrl").setValue(imageReference);
                            logout();
                        }
                    });
                }
            });
        }
        else {
            logout();
        }
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        openLoginScreen();
    }

    // Opens Login Screen
    public void openLoginScreen() {
        Intent openScreen = new Intent(this, MainActivity.class);
        startActivity(openScreen);
        finish();
    }

    // Displays when any field is left blank
    public void displayEmptyFieldError() {
        Toast.makeText(CreateAccount.this, "Fill in all information",Toast.LENGTH_SHORT).show();
    }

    // Displays when an incorrect email is provided
    public void displayMalformedEmailError() {
        Toast.makeText(CreateAccount.this, "Must provide an email ending in @student.csulb.edu",Toast.LENGTH_SHORT).show();
    }

    // Get the information entered by the user
    public void getUserInputs() {
        fNameEt = findViewById(R.id.etFirstName);
        lNameEt = findViewById(R.id.etLastName);
        idNumberEt = findViewById(R.id.etIDNumber);
        emailEt = findViewById(R.id.etEmail);
        passwordEt = findViewById(R.id.etPassword);
        gradDateEt = findViewById(R.id.etGradDate);
        phoneNumEt = findViewById(R.id.etPhone);
    }

    // Checks if any text field is left empty
    public boolean anyEmptyFields() {
        return fNameEt.getText().toString().isEmpty()
                || lNameEt.getText().toString().isEmpty()
                || fNameEt.getText().toString().isEmpty()
                || idNumberEt.getText().toString().isEmpty()
                || emailEt.getText().toString().isEmpty()
                || passwordEt.getText().toString().isEmpty()
                || gradDateEt.getText().toString().isEmpty()
                || phoneNumEt.getText().toString().isEmpty();
    }

    // Checks if email entered is a csulb one
    public boolean isValidSchoolEmail(String email) {
        return email.substring(email.length() - 18).equals("@student.csulb.edu");
    }

    // Sends validation email to user after they create an account
    public void sendValidationEmail(final FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccount.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateAccount.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}