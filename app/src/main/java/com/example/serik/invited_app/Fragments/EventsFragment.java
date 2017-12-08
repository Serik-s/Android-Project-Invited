package com.example.serik.invited_app.Fragments;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.serik.invited_app.Activities.LoginActivity;
import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by Serik on 27.11.2017.
 */

public class EventsFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    Gson gson;

//    @Inject
    Context mContext;

    RecyclerView recyclerView;
    FrameLayout progressBar;
    EventsAdapter eventsAdapter;
    View view;
    private FirebaseAuth mAuth;


    public static List<Event> eventList = new ArrayList<>();
    private static String fragmentTAG = "EVENTS FRAGMENT";
    private static String databaseTAG = "FIREBASE DATABASE";

    // newInstance constructor for creating fragment with arguments
    public static EventsFragment newInstance() {
        Log.e(fragmentTAG, "we are on method newInstance");

        Bundle args = new Bundle();

        EventsFragment allEvents = new EventsFragment();
        allEvents.setArguments(args);
        return allEvents;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventsRef = database.getReference("Events");
        readData(eventsRef);


        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.events_activity, container, false);
        progressBar = (FrameLayout) view.findViewById(R.id.progress);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        Log.e(fragmentTAG, "this is list of events " + eventList);

        return view;
    }

    private void readData(DatabaseReference myRef) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.e(databaseTAG, "this is dataSnapshot " + dataSnapshot);
                Object value = dataSnapshot.getValue(Object.class);
                Log.d(TAG, "Value is: " + value);

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String eventTitle = (String) messageSnapshot.child("eventTitle").getValue();
                    String eventDate = (String) messageSnapshot.child("eventDate").getValue();
                    String eventDescription = (String) messageSnapshot.child("eventDescription").getValue();
                    long eventPopulation = (long) messageSnapshot.child("eventPopulation").getValue();
                    String eventImage = (String) messageSnapshot.child("eventImage").getValue();

                    Event event = new Event(eventTitle, eventDate, eventDescription, eventPopulation, eventImage);
                    Log.e(fragmentTAG, event.toString());
                    eventList.add(event);
                }
                Log.e(fragmentTAG, "this is updated list of events " + eventList);

                eventsAdapter = new EventsAdapter(getActivity(), eventList, mAuth);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(eventsAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void readUserData(DatabaseReference userRef) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue(Object.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    private void iWillGoToEvent() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference users = database.getReference("Users");
////        DatabaseReference user = users.child(mAuth.getCurrentUser().)
//
//
//    }

//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this, view);
//
//        Log.e(fragmentTAG, "We are on EVENTS FRAGMENT");
//        for (int i = 0; i < 10; i++){
//            Event testEvent = new Event();
//            eventList.add(testEvent);
//        }
//        Log.e(fragmentTAG, "this is list of events " + eventList);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
//        mRecyclerView.setLayoutManager(layoutManager);
//        eventsAdapter = new EventsAdapter(mContext, eventList);
//        mRecyclerView.setAdapter(eventsAdapter);
//    }

//    @Override
//    protected int getLayoutRes() {
//        return R.layout.events_activity;
//    }
//



}
