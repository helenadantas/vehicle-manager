package com.vehiclemanager.vehiclemanager.dto;

import java.util.List;

public record VehicleUserDto(List<VehicleItemDto> vehicles,
                             int page,
                             int pageSize,
                             int totalPages,
                             long totalElements) {
}
