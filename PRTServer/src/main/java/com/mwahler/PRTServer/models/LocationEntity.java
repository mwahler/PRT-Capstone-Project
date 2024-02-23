package com.mwahler.PRTServer.models;

import java.util.Objects;

public class LocationEntity {

    private double latitude;
    private double longitude;

    public LocationEntity() {

    }
    public LocationEntity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "Location{" + "lat='" + latitude + "', lon=" + longitude + +'\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity locationEntity = (LocationEntity) o;
        return Objects.equals(latitude, locationEntity.latitude) && Objects.equals(longitude, locationEntity.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}