package com.example.serik.invited_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.serik.invited_app.Activities.EventDetails;
import com.example.serik.invited_app.Activities.MainActivity;
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Serik on 27.11.2017.
 */

public class EventsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<Event> eventList;
    private static String adapterTAG = "ADAPTER EVENT";

    public EventsAdapter(Context context, List<Event> eventList) {
        mContext = context;
        this.eventList = eventList;
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
        Log.e("ViewHolder", "Bind");
        Log.e("position", String.valueOf(position));
        MyViewHolder viewHolder = (MyViewHolder) holder;
        Event event = eventList.get(position);
        viewHolder.setPosition(position);
        viewHolder.eventTitle.setText(event.getEventTitle());
        viewHolder.eventDate.setText(event.getDate());
        viewHolder.eventPopulation.setText("Number of people coming: " + event.getEventPopulation());
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
        int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            eventDate = (TextView) itemView.findViewById(R.id.event_date);
            eventPopulation = (TextView) itemView.findViewById(R.id.event_population);
            likeButton = (Button) itemView.findViewById(R.id.i_will_go_button);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeEvent();
                }
            });

            itemView.setOnClickListener(this);
        }

        public void likeEvent() {
            MainActivity.user.visitingEvents.add(eventList.get(position));
            Log.e(adapterTAG, "added new event to user");
            Log.e(adapterTAG, MainActivity.user.visitingEvents.toString());
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
