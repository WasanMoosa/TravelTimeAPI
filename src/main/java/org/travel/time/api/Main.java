package org.travel.time.api;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class that estimates the travel time between two points.
 */
public class Main {


    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String pickup;
        String dropoff;
        ManageResponse manageResponse = new ManageResponse();
        final String FILE_NAME = "response";
        File file = new File(FILE_NAME);
        System.out.println("I estimate travel time between the two points");

        // If file exist read from File and extract the travel time from File
        if (file.exists()) {
            manageResponse.readFile(FILE_NAME).travelTime();
        } else {
            // If file not exist request new API
            RequestingAPI requestingAPI = new RequestingAPI();

            // take information from args
            if (args.length == 2) {
                pickup = args[0];
                dropoff = args[1];
            }
            // If no args, ask users
            else {
                System.out.print("Write pickup point: ");
                pickup = input.nextLine();
                System.out.print("Write drop Off point: ");
                dropoff = input.nextLine();
            }
            // Extract the travel time from the requested API response
            String responseBody = requestingAPI.request(pickup, dropoff);
            manageResponse.readWeb(responseBody).travelTime();

            // Write the whole response to a file
            manageResponse.WriteFile(responseBody, FILE_NAME);
        }
    }
}