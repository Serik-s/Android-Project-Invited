package com.example.serik.invited_app.Activities;

import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.serik.invited_app.Models.Feedback;
import com.example.serik.invited_app.Models.User;
import com.example.serik.invited_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


/**
 * Created by Serik on 30.11.2017.
 */

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener  {
    FirebaseAuth mAuth;
    EditText mFeedbackTextField;
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;
    Button buttonFive;
    String dateString;


    private int rating = 0;
    private int previousRatingRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);

        mAuth = FirebaseAuth.getInstance();
        mFeedbackTextField = findViewById(R.id.feedback_field);
        buttonOne = findViewById(R.id.star_one);
        buttonTwo = findViewById(R.id.star_two);
        buttonThree = findViewById(R.id.star_three);
        buttonFour = findViewById(R.id.star_four);
        buttonFive = findViewById(R.id.star_five);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);

        dateString = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.star_one:
                Log.e("Previous Star Rate ","" + previousRatingRate);
                rating = 1;
                paintStars(rating, previousRatingRate);
                break;

            case R.id.star_two:
                rating = 2;
                paintStars(rating, previousRatingRate);

                break;

            case R.id.star_three:
                rating = 3;
                paintStars(rating, previousRatingRate);
                break;

            case R.id.star_four:
                rating = 4;
                paintStars(rating, previousRatingRate);
                break;
            case R.id.star_five:
                rating = 5;
                paintStars(rating, previousRatingRate);
                break;
            default:
                break;
        }
    }

    private void paintStars(int ratingRate, int previousRate) {
        if (ratingRate != previousRate) {
            if (ratingRate == 1) {
                paintOneStar();
            } else if (ratingRate == 2) {
                paintTwoStars();
            } else if (ratingRate == 3) {
                paintThreeStars();
            } else if (ratingRate == 4) {
                paintFourStars();
            } else {
                paintAllStars();
            }
            previousRatingRate = ratingRate;
        } else {
            Log.e("Previous Star paint","" + previousRatingRate);
            clearAllStars();
            previousRatingRate = 0;
        }
    }

    private void clearAllStars() {
        buttonOne.setBackground(getResources().getDrawable(R.drawable.unselected_star));
        buttonTwo.setBackground(getResources().getDrawable(R.drawable.unselected_star));
        buttonThree.setBackground(getResources().getDrawable(R.drawable.unselected_star));
        buttonFour.setBackground(getResources().getDrawable(R.drawable.unselected_star));
        buttonFive.setBackground(getResources().getDrawable(R.drawable.unselected_star));
    }
    private void paintOneStar() {
        clearAllStars();
        buttonOne.setBackground(getResources().getDrawable(R.drawable.selected_star));

    }
    private void paintTwoStars() {
        clearAllStars();
        buttonOne.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonTwo.setBackground(getResources().getDrawable(R.drawable.selected_star));
    }
    private void paintThreeStars() {
        clearAllStars();
        buttonOne.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonTwo.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonThree.setBackground(getResources().getDrawable(R.drawable.selected_star));
    }
    private void paintFourStars() {
        clearAllStars();
        buttonOne.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonTwo.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonThree.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonFour.setBackground(getResources().getDrawable(R.drawable.selected_star));
    }
    private void paintAllStars() {
        clearAllStars();
        buttonOne.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonTwo.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonThree.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonFour.setBackground(getResources().getDrawable(R.drawable.selected_star));
        buttonFive.setBackground(getResources().getDrawable(R.drawable.selected_star));
    }

    public void sendFeedback(View v) {
        if (!validateForm()) {
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference feedbackRef = database.getReference("Feedback");

        String feedbackText = mFeedbackTextField.getText().toString();
        String username = mAuth.getCurrentUser().getDisplayName();
        String email = mAuth.getCurrentUser().getEmail();
        feedbackRef.updateChildren(Feedback.toMap(username, email, feedbackText, dateString, rating));

        finish();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mFeedbackTextField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mFeedbackTextField.setError("Required.");
            valid = false;
        } else {
            mFeedbackTextField.setError(null);
        }

        return valid;
    }
}
