package com.mwahler.PRTServer.datatransferobjects;

import com.mwahler.PRTServer.models.DataPointEntity;
import com.mwahler.PRTServer.models.LocationEntity;
import org.bson.types.ObjectId;

import java.util.Date;

public record DataPointDTO (
        String id,
        Date timestamp,
        String carId,
        double temperature,
        double current,
        LocationEntity location) {

    public DataPointDTO(DataPointEntity p) {
        this(p.getId() == null ? new ObjectId().toHexString() : p.getId().toHexString(), p.getTimestamp(),
                p.getCarId().toHexString(), p.getCurrent(), p.getTemperature(), p.getLocation());
    }

    public DataPointEntity toDataPointEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        ObjectId _carId = new ObjectId(carId);
        return new DataPointEntity(_id, timestamp, _carId, current, temperature, location);
    }

}