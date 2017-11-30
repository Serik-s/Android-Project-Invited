package com.example.serik.invited_app.Activities;

import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serik on 30.11.2017.
 */

public class VisitedEventsActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    EventsAdapter eventsAdapter;
    List<Event> eventList = new ArrayList<>();


    private int rating = 0;
    private int previousRatingRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visited_events_activity);

        mAuth = FirebaseAuth.getInstance();
    }



}
