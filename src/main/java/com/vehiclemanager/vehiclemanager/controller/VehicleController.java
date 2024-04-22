package com.vehiclemanager.vehiclemanager.controller;

import com.vehiclemanager.vehiclemanager.dto.CreateVehicleDto;
import com.vehiclemanager.vehiclemanager.dto.UpdateVehicleDto;
import com.vehiclemanager.vehiclemanager.dto.VehicleUserDto;
import com.vehiclemanager.vehiclemanager.entities.User;
import com.vehiclemanager.vehiclemanager.entities.Vehicle;
import com.vehiclemanager.vehiclemanager.services.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles/{vehicleId}")
    @Operation(summary = "Get Vehicle", description = "Get Vehicle by Id", tags = "Vehicles Endpoints")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(vehicle);
    }
    @GetMapping("/vehicles/user/{userId}")
    @Operation(summary = "Get Vehicles by userId", description = "Get Vehicles by userId", tags = "Vehicles Endpoints")
    public ResponseEntity<VehicleUserDto> getVehicleByUserId(@PathVariable UUID userId,
                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        VehicleUserDto vehicles = vehicleService.getVehicleByUserId(userId, page, pageSize);
        return ResponseEntity.ok(vehicles);
    }
    @GetMapping("/vehicles")
    @Operation(summary = "List Vehicles", description = "List all vehicles", tags = "Vehicles Endpoints")
    public ResponseEntity<VehicleUserDto> listVehicles(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        VehicleUserDto vehicles = vehicleService.listVehicles(page, pageSize);
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/vehicles")
    @Operation(summary = "Add Vehicle", description = "Add a new vehicles", tags = "Vehicles Endpoints")
    public ResponseEntity<Void> addVehicle(@RequestBody CreateVehicleDto dto,
                                           JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        vehicleService.addVehicle(dto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/vehicles/{id}")
    @Operation(summary = "Delete Vehicle", description = "Delete an existent vehicle by id", tags = "Vehicles Endpoints")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") Long vehicleId,
                                              JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        vehicleService.deleteVehicle(vehicleId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/vehicles/{vehicleId}")
    @Operation(summary = "Edit Vehicle", description = "Edit an existent vehicle", tags = "Vehicles Endpoints")
    public ResponseEntity<Void> updateVehicle(@PathVariable("vehicleId") Long vehicleId,
                                              @RequestBody UpdateVehicleDto dto,
                                              JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        vehicleService.updateVehicle(vehicleId, dto, userId);
        return ResponseEntity.ok().build();
    }
}