package com.example.serik.invited_app.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.R;

/**
 * Created by Serik on 27.11.2017.
 */

public class EventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Event article = getIntent().getParcelableExtra("event");

        TextView title = (TextView) findViewById(R.id.articleTitle);
        TextView text = (TextView) findViewById(R.id.articleText);
        TextView date = (TextView) findViewById(R.id.articleDate);
//        ImageView image = (ImageView) findViewById(R.id.articleImage);
//
//        image.setImageResource(R.drawable.dota);
        title.setText(article.getEventTitle());
        text.setText(article.getEventDescription());
        date.setText(article.getDate());
    }
}
