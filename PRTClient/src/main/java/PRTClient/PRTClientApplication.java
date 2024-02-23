package PRTClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

public class PRTClientApplication {
    private static final String API_URL = "http://localhost:30080/api";
    private static final String DATAPOINT_ENDPOINT = "/dataPoint";
    private static final String MAC_ENDPOINT = "/car/mac/";
    private static final String CAR_ENDPOINT = "/car";

    public static void main(String[] args) {

        Random random = new Random();

        HttpClient httpClient = HttpClient.newHttpClient();
        try {
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
        //get mac address for pi, the unique identifier


        String macAddr = "";
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface netInt = networkInterfaces.nextElement();
            if (!netInt.isLoopback() && netInt.isUp()) {
                byte[] hardwareAddr = netInt.getHardwareAddress();
                if (hardwareAddr != null) {
                    String[] hexadecimal = new String[hardwareAddr.length];
                    for (int i = 0; i < hardwareAddr.length; i++) {
                        //Formats as 2 character hex, with leading 0 if needed
                        hexadecimal[i] = String.format("%02X", hardwareAddr[i]);
                    }
                    //Creates XX-XX-XX-XX string
                    macAddr = String.join("-", hexadecimal);
                    System.out.println("My Mac Address: " + macAddr);
                    break;
                }
            }
        }


        //See if this car exists in the server
        System.out.println("Attempting to hit: " + new URI(API_URL + MAC_ENDPOINT + macAddr));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL + MAC_ENDPOINT + macAddr))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        //Error code, Car was not found
        if (response.statusCode() == 404) {
            //Create a new car with my mac address
            response = sendPostRequest(httpClient, CAR_ENDPOINT, "{\"macAddress\":\"%s\"}".formatted(macAddr));
        }
        //Json converting to Java object
        CarDTO newCar = new Gson().fromJson(response.body(), CarDTO.class);
        return newCar.id;
    }

    private static HttpResponse<String> sendPostRequest(HttpClient httpClient, String path, String requestBody) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL + path))
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
        return new DataPointDTO(timeStamp, carId, randomTemp, randomCurrent, randomLocation);
    }
}
