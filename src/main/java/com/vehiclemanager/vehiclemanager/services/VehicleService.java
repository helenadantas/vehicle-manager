package com.vehiclemanager.vehiclemanager.services;

import com.vehiclemanager.vehiclemanager.dto.CreateVehicleDto;
import com.vehiclemanager.vehiclemanager.dto.UpdateVehicleDto;
import com.vehiclemanager.vehiclemanager.dto.VehicleItemDto;
import com.vehiclemanager.vehiclemanager.dto.VehicleUserDto;
import com.vehiclemanager.vehiclemanager.entities.Vehicle;
import com.vehiclemanager.vehiclemanager.repository.UserRepository;
import com.vehiclemanager.vehiclemanager.repository.VehicleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public VehicleUserDto listVehicles(int page, int pageSize) {
        var vehiclesPage = vehicleRepository.findAll(
                PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"));

        var vehicleItems = vehiclesPage.getContent().stream()
                .map(vehicle ->
                        new VehicleItemDto(
                                vehicle.getVehicleId(),
                                vehicle.getVehicleModel(),
                                vehicle.getSerialTracker(),
                                vehicle.getLicensePlate(),
                                vehicle.getUser().getEmail())
                )
                .collect(Collectors.toList());

        return new VehicleUserDto(
                vehicleItems,
                page,
                pageSize,
                vehiclesPage.getTotalPages(),
                vehiclesPage.getTotalElements()
        );
    }
    public void addVehicle(CreateVehicleDto dto, UUID userId) {
        var user = userRepository.findById(userId).orElseThrow();

        var vehicle = new Vehicle();
        vehicle.setUser(user);
        vehicle.setVehicleModel(dto.vehicleModel());
        vehicle.setSerialTracker(dto.serialTracker());
        vehicle.setLicensePlate(dto.licensePlate());

        vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long vehicleId, UUID userId) {
        var vehicle = vehicleRepository.findById(vehicleId).orElseThrow();

        if (vehicle.getUser().getUserId().equals(userId)) {
            vehicleRepository.deleteById(vehicleId);
        } else {
            throw new IllegalStateException("User is not authorized to delete this vehicle.");
        }
    }

    public void updateVehicle(Long vehicleId, UpdateVehicleDto dto, UUID userId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow();

        if (!vehicle.getUser().getUserId().equals(userId)) {
            throw new IllegalStateException("User is not authorized to update this vehicle.");
        }

        if (dto.vehicleModel() != null) {
            vehicle.setVehicleModel(dto.vehicleModel());
        }
        if (dto.serialTracker() != null) {
            vehicle.setSerialTracker(dto.serialTracker());
        }
        if (dto.licensePlate() != null) {
            vehicle.setLicensePlate(dto.licensePlate());
        }
        vehicleRepository.save(vehicle);
    }
}
