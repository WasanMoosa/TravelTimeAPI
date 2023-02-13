package org.travel.time.api;

import okhttp3.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

/**
 * Class that estimates the travel time between two points.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize variables to hold pickup and dropoff points
        String pickup = "";
        String dropoff = "";
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("I estimate travel time between the two points");
            System.out.print("Write pickup point: ");
            pickup = input.nextLine();
            System.out.print("Write drop Off point: ");
            dropoff = input.nextLine();

            // Set up the HTTP client
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");

            // API Key for the Google Maps API
            String apiKey = "API KEY";

            // Construct the URL for the API request
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + pickup + "&destinations=" + dropoff + "&key=" + apiKey;

            // Create the API request
            Request request = new Request.Builder().url(url).get().build();

            // Send the API request and get the response
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // Extract the travel time from the API response
            travelTime(responseBody);
        } catch (Exception e) {
            System.out.println("Something went wrong, try again");
        }
    }

    /**
     * Extract the travel time from the API response.
     *
     * @param responseBody The JSON response from the API.
     */
    public static void travelTime(String responseBody) throws IOException {
        // Use Gson to parse the JSON response
        Gson gson = new Gson();
        Map<String, Object> response = gson.fromJson(responseBody, Map.class);

        // Get the status of the response
        String status = (String) response.get("status");
        status = status.toLowerCase();

        // If the response status is OK, extract the travel time
        if (status.equals("ok")) {
            ArrayList<Object> rows = (ArrayList<Object>) response.get("rows");
            Map<String, Object> rowMap = (Map<String, Object>) rows.get(0);
            ArrayList<Object> elements = (ArrayList<Object>) rowMap.get("elements");
            Map<String, Object> elementMap = (Map<String, Object>) elements.get(0);
            Map<String, Object> duration = (Map<String, Object>) elementMap.get("duration");
            String durationText = (String) duration.get("text");
            System.out.println("The Estimated duration time is " + durationText);
            // If the response status is not OK, extract the status
        } else {
            System.out.println(status);
        }
    }
}