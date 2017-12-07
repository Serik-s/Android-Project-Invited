package com.example.serik.invited_app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serik.invited_app.Activities.EditUserNameActivity;
import com.example.serik.invited_app.Activities.FeedbackActivity;
import com.example.serik.invited_app.Activities.LoginActivity;
import com.example.serik.invited_app.Activities.MainActivity;
import com.example.serik.invited_app.Activities.VisitedEventsActivity;
import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serik on 27.11.2017.
 */

public class ProfileFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private FirebaseAuth mAuth;

    EventsAdapter eventsAdapter;
    List<Event> eventList = new ArrayList<>();

    View view;
    TextView userFullName;
    ImageView userImage;
//        AppDatabase database;

    // newInstance constructor for creating fragment with arguments
    public static ProfileFragment newInstance() {
        ProfileFragment user = new ProfileFragment();
        Bundle args = new Bundle();
        user.setArguments(args);
        return user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.profile_activity, container, false);
        userImage = (ImageView) view.findViewById(R.id.user_image);
        userFullName = (TextView) view.findViewById(R.id.user_fullname);
        TextView userEmail = (TextView) view.findViewById(R.id.user_email);
        LinearLayout logOutButton = (LinearLayout) view.findViewById(R.id.logout);
        LinearLayout editName = (LinearLayout) view.findViewById(R.id.edit_name);
        LinearLayout feedback = (LinearLayout) view.findViewById(R.id.feedback_activity);
        LinearLayout visitedEvents = (LinearLayout) view.findViewById(R.id.visited_events_activity);



        Picasso.with(getContext())
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .placeholder(R.drawable.user_black)
                .error(R.drawable.user_black)
                .into(userImage);


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outFragment();
            }
        });
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToEditActivity();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToFeedbackActivity();
            }
        });
        visitedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToVisitedEventsActivity();
            }
        });

        if (mAuth != null && mAuth.getCurrentUser() != null) {
            userEmail.setText(mAuth.getCurrentUser().getEmail());
            userFullName.setText(mAuth.getCurrentUser().getDisplayName());
        }

        return view;
    }

    public void outFragment() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        EventsFragment.eventList = new ArrayList<>();
        updateUI(null);
    }

    public void moveToEditActivity() {
        Intent intent = new Intent(this.getActivity(), EditUserNameActivity.class);
        startActivityForResult(intent, 101);
    }

    public void moveToFeedbackActivity() {
        Intent intent = new Intent(this.getActivity(), FeedbackActivity.class);
        startActivity(intent);
    }


    public void moveToVisitedEventsActivity() {
        Intent intent = new Intent(this.getActivity(), VisitedEventsActivity.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == getActivity().RESULT_OK){
            Log.e("onActivityResult", "OK");
//            mAuth = FirebaseAuth.getInstance();
            userFullName.setText(data.getStringExtra("name"));
        }
    }
}
