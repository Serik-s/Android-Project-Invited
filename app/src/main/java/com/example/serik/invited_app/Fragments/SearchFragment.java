package com.example.serik.invited_app.Fragments;

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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Serik on 27.11.2017.
 */

public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    FrameLayout progressBar;
    EventsAdapter eventsAdapter;
    View view;
    EditText searchingEventName;


    List<Event> eventList = new ArrayList<>();
    List<Event> foundedEvents = new ArrayList<>();
    private static String fragmentTAG = "EVENTS FRAGMENT";
    private static String databaseTAG = "FIREBASE DATABASE";

    // newInstance constructor for creating fragment with arguments
    public static SearchFragment newInstance() {
        SearchFragment foundedEvents = new SearchFragment();
        Bundle args = new Bundle();
        foundedEvents.setArguments(args);
        return foundedEvents;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");

        readData(myRef);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_activity, container, false);
        progressBar = (FrameLayout) view.findViewById(R.id.search_progress);
        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycler);
        searchingEventName = (EditText) view.findViewById(R.id.founded_event);


        Button searchEvent = (Button) view.findViewById(R.id.find_button);


        searchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEvent();
            }
        });

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

                eventsAdapter = new EventsAdapter(getActivity(), foundedEvents);
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

    public void searchEvent() {
        String searchingName = searchingEventName.getText().toString();
        foundedEvents = new ArrayList<>();
        for(int i = 0; i < EventsFragment.eventList.size(); i++) {
            if (eventList.get(i).getEventTitle().contains(searchingName)) {
                foundedEvents.add(eventList.get(i));
            }
        }

        eventsAdapter = new EventsAdapter(getActivity(), foundedEvents);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(eventsAdapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

    }


}
