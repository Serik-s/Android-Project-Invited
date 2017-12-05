package com.example.serik.invited_app.Models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serik on 30.11.2017.
 */

public class Feedback {
    private String username;
    private String email;
    private String text;
    private String date;
    private long rating;

    public Feedback(String username, String email, String text, String date, long rating) {
        this.username = username;
        this.email = email;
        this.text = text;
        this.date = date;
        this.rating = rating;
    }

    public static HashMap<String, Object> asMap(String userName, String email, String text, String date, long rating) {
        HashMap<String, Object> result = new HashMap<>();

        result.put("name", userName);
        result.put("email", email);
        result.put("Message", text);
        result.put("Date", date);
        result.put("Rating", rating);

        return result;
    }

    @Exclude
    public static Map<String, Object> toMap(Feedback feedback) {
        HashMap<String, Object> result = new HashMap<>();

        result.put("name", feedback.username);
        result.put("email", feedback.email);
        result.put("Message", feedback.text);
        result.put("Date", feedback.date);
        result.put("Rating", feedback.rating);

        return result;
    }
}
