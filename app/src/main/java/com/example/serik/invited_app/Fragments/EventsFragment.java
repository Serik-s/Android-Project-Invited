package com.example.serik.invited_app.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Serik on 27.11.2017.
 */

public class EventsFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    EventsAdapter eventsAdapter;
    List<Event> eventList = new ArrayList<>();
//        AppDatabase database;

    // newInstance constructor for creating fragment with arguments
    public static EventsFragment newInstance() {
        EventsFragment allEvents = new EventsFragment();
        Bundle args = new Bundle();
        allEvents.setArguments(args);
        return allEvents;
    }


}
