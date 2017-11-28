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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.serik.invited_app.Activities.LoginActivity;
import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;
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

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    EventsAdapter eventsAdapter;
    List<Event> eventList = new ArrayList<>();
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");

        readData(myRef);

        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.events_activity, container, false);
//        TextView tvLabel = (TextView) view.findViewById(R.id.);
//        tvLabel.setText(page + " -- " + title);
//        Log.e(fragmentTAG, "We are on EVENTS FRAGMENT");
//        for (int i = 0; i < 10; i++){
//            Event testEvent = new Event();
//            eventList.add(testEvent);
//        }
        Log.e(fragmentTAG, "this is list of events " + eventList);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        eventsAdapter = new EventsAdapter(getActivity(), eventList);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(eventsAdapter);

//            new DatabaseAsync().execute();
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

                    Event event = new Event(eventTitle, eventDate, eventDescription, eventPopulation);
                    Log.e(fragmentTAG, event.toString());
                    eventList.add(event);
                }
                Log.e(fragmentTAG, "this is updated list of events " + eventList);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

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
