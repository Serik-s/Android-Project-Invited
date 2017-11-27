package com.example.serik.invited_app.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serik on 27.11.2017.
 */

public class SearchFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    EventsAdapter eventsAdapter;
    List<Event> eventList = new ArrayList<>();
//        AppDatabase database;

    // newInstance constructor for creating fragment with arguments
    public static SearchFragment newInstance() {
        SearchFragment foundedEvents = new SearchFragment();
        Bundle args = new Bundle();
        foundedEvents.setArguments(args);
        return foundedEvents;
    }


}
