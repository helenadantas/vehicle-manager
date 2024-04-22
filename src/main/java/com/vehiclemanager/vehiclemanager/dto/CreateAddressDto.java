package com.vehiclemanager.vehiclemanager.dto;

public record CreateAddressDto(String street, String neighborhood, String number, String cep, String complement, String city, String state) {
}
