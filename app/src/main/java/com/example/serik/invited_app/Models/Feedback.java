package com.example.serik.invited_app.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serik on 30.11.2017.
 */

public class Feedback {

    public static Map<String, Object> toMap(String userName, String email, String text,String date,long rating) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", userName);
        result.put("email", email);
        result.put("Message", text);
        result.put("Date", date);
        result.put("Rating", rating);

        return result;
    }
}
