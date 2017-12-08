package com.example.serik.invited_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.serik.invited_app.Models.User;
import com.example.serik.invited_app.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Serik on 26.11.2017.
 */

public class RegistrationActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private ProgressBar progressBar;

    private static final String facebookTAG = "Facebook Login";
    private static final String emailTAG = "Email Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Log.e(emailTAG, "We are on Registration page");
        // Edit Texts
        mEmailField = findViewById(R.id.mail_field);
        mPasswordField = findViewById(R.id.password_field);

        mAuth = FirebaseAuth.getInstance();
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    public void signUp(View v) {
        Button loginButton = (Button) findViewById(R.id.sign_up_button);
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        Log.d(emailTAG, "these are mail " + email + " password " + password);
        createAccount(email, password);
    }
    private void createAccount(String email, String password) {

        Log.e(emailTAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        progressBar = new ProgressBar(RegistrationActivity.this,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        layout.addView(progressBar);
        progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(emailTAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(emailTAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        progressBar.setVisibility(View.GONE);
                        // [END_EXCLUDE]
                    }
                });


        // [END create_user_with_email]
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }


        return valid;
    }


    private void updateUI(FirebaseUser user) {
        Log.e(facebookTAG, "firebase user " + user);
//        hideProgressDialog();
        if (user != null) {
            Log.d(emailTAG, mAuth.getCurrentUser().getEmail());
            Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void moveToLoginPage(View v) {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
