package com.example.serik.invited_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serik.invited_app.Activities.EventDetails;
import com.example.serik.invited_app.Activities.MainActivity;
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.Models.Feedback;
import com.example.serik.invited_app.Models.User;
import com.example.serik.invited_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Serik on 27.11.2017.
 */

public class EventsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<Event> eventList;
    private FirebaseAuth mAuth;
    private static String adapterTAG = "ADAPTER EVENT";

    public EventsAdapter(Context context, List<Event> eventList, FirebaseAuth mAuth) {
        mContext = context;
        this.eventList = eventList;
        this.mAuth = mAuth;
        for (int i = 0; i < eventList.size(); i++){
            Log.e(adapterTAG, "we are on eventsAdapter constructor");
            System.out.println(eventList.get(i).toString());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("ViewHolder", "Create");
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (eventList.get(position) != null) {
            Log.e("ViewHolder", "Bind");
            Log.e("position", String.valueOf(position));
            MyViewHolder viewHolder = (MyViewHolder) holder;
            Event event = eventList.get(position);
            event.setEventID(position);
            viewHolder.setPosition(position);
            viewHolder.eventTitle.setText(event.getEventTitle());
            viewHolder.eventDate.setText(event.getDate());
            viewHolder.eventPopulation.setText("Number of people coming: " + event.getEventPopulation());
        }

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView eventTitle;
        TextView eventDate;
        TextView eventPopulation;
        Button likeButton;
        String key;
        int position;
        DatabaseReference mDatabase;

        int likeCase = 1;
        public MyViewHolder(View itemView) {
            super(itemView);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            eventDate = (TextView) itemView.findViewById(R.id.event_date);
            eventPopulation = (TextView) itemView.findViewById(R.id.event_population);
            likeButton = (Button) itemView.findViewById(R.id.i_will_go_button);

            if (MainActivity.user.visitingEvents.contains(eventList.get(position))) {
                likeCase = 0;
                likeButton.setText("i will not go");
                likeButton.setBackground(mContext.getDrawable(R.drawable.not_go_button));
            }


            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (likeCase == 1) {
                        likeEvent();
                        likeCase = 0;
                    } else {
                        dislikeEvent();
                        likeCase = 1;
                    }
                }
            });

            itemView.setOnClickListener(this);
        }

        public void likeEvent() {
            mDatabase = FirebaseDatabase.getInstance().getReference();

            key = mDatabase.child("Users").push().getKey();
            ArrayList<Event> currentList = MainActivity.user.visitingEvents;

            Map<String, Object> eventValues = Event.toMap(eventList.get(position));
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/Users/" + mAuth.getCurrentUser().getUid() + "/" + position, eventValues);

            mDatabase.updateChildren(childUpdates);
            Log.e(adapterTAG, "size of user's event list is: " + MainActivity.user.visitingEvents.size());

            MainActivity.user.visitingEvents.add(eventList.get(position));

            Log.e(adapterTAG, "added new event to user");
            Log.e(adapterTAG, MainActivity.user.visitingEvents.toString());

            likeButton.setText("i will not go");
            Toast.makeText(mContext, "See you soon one event :)", Toast.LENGTH_SHORT).show();
            likeButton.setBackground(mContext.getDrawable(R.drawable.not_go_button));
        }

        public void dislikeEvent() {
            mDatabase = FirebaseDatabase.getInstance().getReference("Users");
            DatabaseReference userRef = mDatabase.child(mAuth.getCurrentUser().getUid());

            MainActivity.user.visitingEvents.remove(eventList.get(position));

            userRef.child(String.valueOf(position)).removeValue();

            Toast.makeText(mContext, "Hope that you will come anyway :(", Toast.LENGTH_SHORT).show();
            likeButton.setBackground(mContext.getDrawable(R.drawable.go_button));
            likeButton.setText("i will go");
        }



        public void setPosition(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), EventDetails.class);
            intent.putExtra("event", eventList.get(position));
            mContext.startActivity(intent);
        }
    }
}
