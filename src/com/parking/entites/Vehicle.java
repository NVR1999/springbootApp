package com.parking.entites;

import com.parking.util.VehicleSize;

public class Vehicle {
    private String licensePlate;
    private VehicleSize size;

    public Vehicle(String licensePlate, VehicleSize size) {
        this.licensePlate = licensePlate;
        this.size = size;
    }

    public VehicleSize getSize() {
        return size;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
