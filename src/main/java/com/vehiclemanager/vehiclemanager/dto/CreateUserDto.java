package com.vehiclemanager.vehiclemanager.dto;

public record CreateUserDto(String email, String password, String name, String cnpj, CreateAddressDto createAddressDto) {
}
