package org.travel.time.api;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class ManageResponse {

    private Map<String, Object> response;

    //read from File

    /**
     * Parses a JSON file into a Map using Gson.
     *
     * @param fileName The name of the file to be parsed.
     * @return A ManageResponse object with the parsed response.
     * @throws RuntimeException if the file is not found.
     */
    public ManageResponse readFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.exists()) {
                Reader reader = new FileReader(file);
                Gson gson = new Gson();
                this.response = (Map<String, Object>) gson.fromJson(reader, Map.class);
            }
        } catch (FileNotFoundException e) {
            System.out.println("can not read from file");
        }
        return this;
    }

    /**
     * Parses the response from a web request into a Map using Gson.
     *
     * @param responseBody The response body from the web request.
     * @return A ManageResponse object with the parsed response.
     */
    public ManageResponse readWeb(String responseBody) {
        Gson gson = new Gson();
        try {
            this.response = gson.fromJson(responseBody, Map.class);
        } catch (Exception e) {
            System.out.println("No connection");
        }
        return this;
    }

    /**
     * Extract the travel time from the API response.
     */
    public void travelTime() {
        try {
            // Get the status of the response
            String status = (String) response.get("status");
            status = status.toLowerCase();

            // If the response status is OK, extract the travel time
            if (status.equals("ok")) {

                // check origin
                ArrayList<Object> origin = (ArrayList<Object>) response.get("origin_addresses");
                String pickup = (String) origin.get(0);

                // Check destination address
                ArrayList<Object> distination = (ArrayList<Object>) response.get("destination_addresses");
                String dropoff = (String) distination.get(0);
                System.out.println(pickup + " -> " + dropoff);
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


        } catch (Exception e) {
            System.out.println("Try again");
        }
    }


    public void WriteFile(String responseBody, String FILE_NAME) {

        File file = new File(FILE_NAME);
        try (FileWriter writer = new FileWriter(file)) {
            // Write the GameState object to the file as a JSON string.
            writer.write(responseBody);

        } catch (Exception e) {
            // Print the error message if the file cannot be written.
            System.out.println("Can not write the response to file");
        }
    }
}
