package com.example.serik.invited_app.Fragments;

import android.content.Context;
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


import com.example.serik.invited_app.Adapters.EventsAdapter;
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Serik on 27.11.2017.
 */

public class EventsFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

//    @Inject
    Context mContext;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    EventsAdapter eventsAdapter;
    List<Event> eventList = new ArrayList<>();
    private static String fragmentTAG = "EVENTS FRAGMENT";

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
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.events_activity, container, false);
//        TextView tvLabel = (TextView) view.findViewById(R.id.);
//        tvLabel.setText(page + " -- " + title);
        Log.e(fragmentTAG, "We are on EVENTS FRAGMENT");
        for (int i = 0; i < 10; i++){
            Event testEvent = new Event();
            eventList.add(testEvent);
        }
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
