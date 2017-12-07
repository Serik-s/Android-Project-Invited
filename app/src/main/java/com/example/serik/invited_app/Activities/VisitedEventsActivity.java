package com.example.serik.invited_app.Activities;

import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serik on 30.11.2017.
 */

public class VisitedEventsActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    EventsAdapter eventsAdapter;
    TextView emptyEventList;

    List<Event> eventList = new ArrayList<>();
    private static String visitedTAG = "VISITED EVENTS BY USER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visited_events_activity);

        mAuth = FirebaseAuth.getInstance();

//        progressBar = findViewById(R.id.user_progress);
        recyclerView = findViewById(R.id.user_visited_events);
        Log.e(visitedTAG, "this is list of events " + eventList);
        emptyEventList = findViewById(R.id.empty_event_list_text_view);


        eventsAdapter = new EventsAdapter(this, MainActivity.user.visitingEvents, mAuth);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(eventsAdapter);

        if (MainActivity.user.visitingEvents.isEmpty()) {
            emptyEventList.setVisibility(View.VISIBLE);
        } else {
            emptyEventList.setVisibility(View.GONE);
        }
    }


//    private void readUserData(DatabaseReference userRef) {
//        userRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        System.out.println(dataSnapshot.getKey());
//
//
//                        ArrayList<Event> myDataset = new ArrayList<Event>();
//
//                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                            System.out.println(dsp.getKey());
//                            System.out.println(dsp.getValue());
//
//                            Event myRestaurant = dsp.getValue(Event.class);
//
////                            myDataset.add(myRestaurant);
//                            user.visitingEvents.add(myRestaurant);
//                            Log.e(mainActivityTAG, "this is event:  " + myRestaurant);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                }
//        );
//    }
}
