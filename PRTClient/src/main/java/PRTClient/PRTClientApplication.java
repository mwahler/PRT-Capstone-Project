package PRTClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

public class PRTClientApplication {
    private static String apiURL = "http://localhost:30080/api";
    private static final String DATAPOINT_ENDPOINT = "/dataPoint";
    private static final String SERIAL_ENDPOINT = "/car/serial/";
    private static final String CAR_ENDPOINT = "/car";

    public static void main(String[] args) {

        Random random = new Random();

        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            String ip = System.getenv("SERVER_IP");
            if (ip != null) {
                //Use environment variable
                apiURL = "http://" + ip+ ":30080/api";
                System.out.println("API URL: " + apiURL);
            }
            String carId = getCarId(httpClient);
            for (int i = 0; i < 10; i++) {
                //Fake the data points
                DataPointDTO randomDataPointDTO = generateRandomDataPointDTO(carId);
                sendData(httpClient, randomDataPointDTO);
                //Wait between 1 and 10 seconds
                Thread.sleep(1000+random.nextInt(9000));
            }
        } catch (URISyntaxException | RuntimeException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static String getCarId(HttpClient httpClient) throws URISyntaxException, IOException, InterruptedException {
        //get serial number for pi, the unique identifier
        String serialNumber = null;
        try(BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"))) {
            String line;
            while ((line = br.readLine()) != null) {
                //Reads each line
                if (line.startsWith("Serial")) {
                    //Array of pieces of the line, split on white space
                    String[] parts = line.split(":\\s+");

                if (parts.length == 2) {
                    //Gets the serial number
                    serialNumber = parts[1].strip();
                    break;
                }
            }}
        }




        //See if this car exists in the server
        System.out.println("Attempting to hit: " + new URI(apiURL + SERIAL_ENDPOINT + serialNumber));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiURL + SERIAL_ENDPOINT + serialNumber))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        //Error code, Car was not found
        if (response.statusCode() == 404) {
            //Create a new car with my serial number
            response = sendPostRequest(httpClient, CAR_ENDPOINT, "{\"serialNumber\":\"%s\"}".formatted(serialNumber));
        }
        //Json converting to Java object
        CarDTO newCar = new Gson().fromJson(response.body(), CarDTO.class);
        return newCar.id;
    }

    private static HttpResponse<String> sendPostRequest(HttpClient httpClient, String path, String requestBody) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiURL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        return response;
    }

    private static void sendData(HttpClient httpClient, DataPointDTO data) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();

        String jsonDataPointDTO = gson.toJson(data);
        sendPostRequest(httpClient, DATAPOINT_ENDPOINT, jsonDataPointDTO);
        System.out.println("Sent DataPoint: " + jsonDataPointDTO);
    }

    private static DataPointDTO generateRandomDataPointDTO(String carId) {
        Random rand = new Random();
        //Current time
        Date timeStamp = new Date();
        //generate randomized data
        double randomTemp = 60 + rand.nextDouble() * 5;
        double randomCurrent = 1000 + rand.nextDouble() * 200;
        //Morgantown area coordinates
        double lat = 39.5523 + rand.nextDouble() * 0.1545;
        double lon = -80.0558 + rand.nextDouble() * 0.1998;
        LocationDTO randomLocation = new LocationDTO(lat, lon);
        return new DataPointDTO(timeStamp, carId, randomCurrent, randomTemp, randomLocation);
    }
}
