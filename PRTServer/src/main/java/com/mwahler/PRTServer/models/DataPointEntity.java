package com.mwahler.PRTServer.models;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

public class DataPointEntity {

    private ObjectId id;
    private Date timestamp;
    private ObjectId carId;
    private double temperature;
    private double current;
    private LocationEntity location;

    public DataPointEntity() {
    }

    public DataPointEntity(ObjectId id,
                           Date timestamp,
                           ObjectId carId,
                           double temperature,
                           double current,
                           LocationEntity location) {
        this.id = id;
        this.timestamp = timestamp;
        this.carId = carId;
        this.temperature = temperature;
        this.current = current;
        this.location = location;
    }
    public ObjectId getId() {
        return id;
    }

    public DataPointEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public ObjectId getCarId() {
        return carId;
    }

    public void setCarId(ObjectId carId) {
        this.carId = carId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "DataPoint{" + "timestamp=" + timestamp + ", carId='" + carId + "', temperature='" + temperature + '\'' + ", current='" + current + '\'' + "location:" + location+ '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPointEntity dataPointEntity = (DataPointEntity) o;
        return Objects.equals(timestamp, dataPointEntity.timestamp)
                && Objects.equals(carId, dataPointEntity.carId)
                && Objects.equals(temperature, dataPointEntity.temperature)
                && Objects.equals(current, dataPointEntity.current)
                && Objects.equals(location, dataPointEntity.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, carId, current, temperature, location);
    }

}
