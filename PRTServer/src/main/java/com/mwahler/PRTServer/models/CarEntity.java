package com.mwahler.PRTServer.models;

import org.bson.types.ObjectId;

import java.util.Objects;

public class CarEntity {
    private ObjectId id;
    private String macAddress;

    public CarEntity() {

    }

    public CarEntity(ObjectId id, String macAddress) {
        this.id = id;
        this.macAddress = macAddress;
    }

    public ObjectId getId() {
        return id;
    }

    public CarEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public CarEntity setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    @Override
    public String toString() {
        return "Car{" + "id='" + id + '\'' + "macAddress='" + macAddress + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity carEntity = (CarEntity) o;
        return Objects.equals(id, carEntity.id)
                && Objects.equals(macAddress, carEntity.macAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, macAddress);
    }
}
