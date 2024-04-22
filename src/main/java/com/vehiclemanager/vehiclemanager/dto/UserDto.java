package com.vehiclemanager.vehiclemanager.dto;

import com.vehiclemanager.vehiclemanager.entities.Address;

import java.util.UUID;

public record UserDto(UUID userId, String email, String name, String cnpj, Address address) {
}
