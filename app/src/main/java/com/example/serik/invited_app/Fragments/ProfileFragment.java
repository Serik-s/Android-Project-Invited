package com.example.serik.invited_app.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;

import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serik on 27.11.2017.
 */

public class ProfileFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    EventsAdapter eventsAdapter;
    List<Event> eventList = new ArrayList<>();
//        AppDatabase database;

    // newInstance constructor for creating fragment with arguments
    public static ProfileFragment newInstance() {
        ProfileFragment user = new ProfileFragment();
        Bundle args = new Bundle();
        user.setArguments(args);
        return user;
    }


}
