package com.example.serik.invited_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

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

    public Event() {

    }

    protected Event(Parcel in) {
        eventID = in.readInt();
        eventTitle = in.readString();
        eventDate = in.readString();
        eventDescription = in.readString();
        imageURL = in.readString();
    }


    // GET METHODS FOR EVENT
    public String getDate() {
        return eventDate;
    }

    public String getEventTitle() { return eventTitle; }

    public String getEventDescription() { return  eventDescription; }

    public int getEventID() { return  eventID; }

    // SET METHODS FOR EVENT
    public void setEventDate(String eventDate) { this.eventDate =  eventDate; }

    public void setEventTitle(String eventTitle) { this.eventTitle =  eventTitle; }

    public void setEventDescription(String eventDescription) { this.eventDescription =  eventDescription; }

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

    }
}
