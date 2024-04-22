package com.vehiclemanager.vehiclemanager.dto;

public record VehicleItemDto(long vehicleId, String vehicleModel, String  serialTracker,
                             String licensePlate, String email) {
}
