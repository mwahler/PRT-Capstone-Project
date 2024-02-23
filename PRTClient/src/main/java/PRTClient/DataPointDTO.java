package PRTClient;

import java.util.Date;

public record DataPointDTO(Date timestamp, String carId, double temperature, double current, LocationDTO location) {

}
