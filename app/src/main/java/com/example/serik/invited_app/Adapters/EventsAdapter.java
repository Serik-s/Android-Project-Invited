package com.example.serik.invited_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;

import java.util.List;

/**
 * Created by Serik on 27.11.2017.
 */

public class EventsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<Event> eventList;


    public EventsAdapter(Context context, List<Event> eventList) {
        mContext = context;
        this.eventList = eventList;
        for (int i = 0; i < eventList.size(); i++){
            System.out.println(eventList.get(i).toString());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("ViewHolder", "Create");
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        return new EventsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e("ViewHolder", "Bind");
        Log.d("position", String.valueOf(position));
        EventsAdapter.MyViewHolder viewHolder = (EventsAdapter.MyViewHolder) holder;
        Event event = eventList.get(position);
        viewHolder.setPosition(position);
        viewHolder.eventTitle.setText(event.getEventTitle());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView eventTitle;
        int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
//            articleDate = (TextView) itemView.findViewById(R.id.articleDate);

//            itemView.setOnClickListener(this);
        }


        public void setPosition(int position){
            this.position = position;
        }

//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(v.getContext(), ArticleDetailsActivity.class);
//            intent.putExtra("article", categoryList.get(position));
//            mContext.startActivity(intent);
//        }
    }
}
