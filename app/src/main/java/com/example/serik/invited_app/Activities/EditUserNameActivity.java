package com.example.serik.invited_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.serik.invited_app.Adapters.MainAdapter;
import com.example.serik.invited_app.Fragments.ProfileFragment;
import com.example.serik.invited_app.Models.User;
import com.example.serik.invited_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.ButterKnife;

/**
 * Created by Serik on 29.11.2017.
 */

public class EditUserNameActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText mUserNameTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_name_activity);

        mAuth = FirebaseAuth.getInstance();
        mUserNameTextField = findViewById(R.id.username_edit_text);

    }

    public void changeUserName(View v) {
        String nameToChange = mUserNameTextField.getText().toString();

        User.changeUserName(mAuth.getCurrentUser(), nameToChange);

        Intent intent = new Intent();
        intent.putExtra("name", nameToChange);
        setResult(RESULT_OK, intent);

        finish();
    }

}
