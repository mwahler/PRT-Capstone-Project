package com.mwahler.PRTServer.datatransferobjects;

import com.mwahler.PRTServer.models.CarEntity;
import org.bson.types.ObjectId;

public record CarDTO(String id, String serialNumber) {

    public CarDTO(CarEntity c) {
        this(c.getId() == null ? new ObjectId().toHexString() : c.getId().toHexString(), c.getSerialNumber());
    }

    public CarEntity toCarEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new CarEntity(_id, serialNumber);
    }
}
