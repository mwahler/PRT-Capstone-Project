package com.mwahler.PRTServer.models;

import org.bson.types.ObjectId;

import java.util.Objects;

public class CarEntity {
    private ObjectId id;
    private String serialNumber;

    public CarEntity() {

    }

    public CarEntity(ObjectId id, String serialNumber) {
        this.id = id;
        this.serialNumber = serialNumber;
    }

    public ObjectId getId() {
        return id;
    }

    public CarEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public CarEntity setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    @Override
    public String toString() {
        return "Car{" + "id='" + id + '\'' + "serialNumber" + "='" + serialNumber + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity carEntity = (CarEntity) o;
        return Objects.equals(id, carEntity.id)
                && Objects.equals(serialNumber, carEntity.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber);
    }
}
