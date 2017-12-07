package com.example.serik.invited_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serik on 13.11.2017.
 */

public class Event implements Parcelable {
    private int eventID;
    private String eventTitle;
    private String eventLocation;
    private String eventDescription;
    private String eventDate;
    private String imageURL;
    private long eventPopulation;

    public Event() {
        eventID = 1;
        eventTitle = "On thrusday we are going to pass android for 100%";
        eventDescription = "It will be very easy and exciting. I'm going to pass in my university at 16:00";
        eventDate = "30th of Novermber";
    }

    public Event(String eventTitle, String eventDate, String eventDescription, long  eventPopulation){
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventPopulation = eventPopulation;
    }

    protected Event(Parcel in) {
        eventID = in.readInt();
        eventTitle = in.readString();
        eventDate = in.readString();
        eventDescription = in.readString();
        eventPopulation = in.readLong();
        imageURL = in.readString();
    }

    @Exclude
    public static Map<String, Object> toMap(Event event) {
        HashMap<String, Object> result = new HashMap<>();

        result.put("eventTitle", event.eventTitle);
        result.put("eventPopulation", event.eventPopulation);
        result.put("eventImage", event.imageURL);
        result.put("eventDescription", event.eventDescription);
        result.put("eventDate", event.eventDate);

        return result;
    }


    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };




    // GET METHODS FOR EVENT
    public String getDate() {
        return eventDate;
    }

    public String getEventTitle() { return eventTitle; }

    public String getEventDescription() { return  eventDescription; }

    public int getEventID() { return  eventID; }

    public long getEventPopulation() { return eventPopulation; }

    // SET METHODS FOR EVENT
    public void setEventDate(String eventDate) { this.eventDate =  eventDate; }

    public void setEventTitle(String eventTitle) { this.eventTitle =  eventTitle; }

    public void setEventDescription(String eventDescription) { this.eventDescription =  eventDescription; }

    public void setEventPopulation(long eventPopulation) { this.eventPopulation = eventPopulation; }

    public void setEventID(int eventID) { this.eventID = eventID; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(eventID);
        parcel.writeString(eventTitle);
        parcel.writeString(eventDescription);
        parcel.writeString(eventDate);
        parcel.writeString(imageURL);
        parcel.writeLong(eventPopulation);
    }

    @Override
    public String toString() {
        return "This is your EVENT: " + eventTitle + " " + eventDescription + " " + eventDate + " " + eventPopulation;
    }
}
