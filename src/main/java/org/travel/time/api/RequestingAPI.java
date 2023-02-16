package org.travel.time.api;

import okhttp3.*;

import java.util.Scanner;

public class RequestingAPI {


    public String request(String origin, String destination) {

        // Initialize variables to hold pickup and dropoff points
        Scanner input = new Scanner(System.in);
        String responseBody = null;
        try {

            // Reading arguments from file
            String pickup = origin;
            String dropoff = destination;

            // Set up the HTTP client
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");

            // API Key for the Google Maps API
            String apiKey = "000";

            // Construct the URL for the API request
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + pickup + "&destinations=" + dropoff + "&key=" + apiKey;

            // Create the API request
            Request request = new Request.Builder().url(url).get().build();

            // Send the API request and get the response
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();

        } catch (Exception e) {
            System.out.println("Something went wrong, try again");
        }
        return responseBody;
    }
}
