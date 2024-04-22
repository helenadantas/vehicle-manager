package com.vehiclemanager.vehiclemanager.controller;

import com.vehiclemanager.vehiclemanager.dto.CreateVehicleDto;
import com.vehiclemanager.vehiclemanager.dto.UpdateVehicleDto;
import com.vehiclemanager.vehiclemanager.dto.VehicleUserDto;
import com.vehiclemanager.vehiclemanager.services.VehicleService;
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

    @GetMapping("/vehicles")
    public ResponseEntity<VehicleUserDto> listVehicles(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        VehicleUserDto vehicles = vehicleService.listVehicles(page, pageSize);
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Void> addVehicle(@RequestBody CreateVehicleDto dto,
                                           JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        vehicleService.addVehicle(dto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") Long vehicleId,
                                              JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        vehicleService.deleteVehicle(vehicleId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/vehicles/{vehicleId}")
    public ResponseEntity<Void> updateVehicle(@PathVariable("vehicleId") Long vehicleId,
                                              @RequestBody UpdateVehicleDto dto,
                                              JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        vehicleService.updateVehicle(vehicleId, dto, userId);
        return ResponseEntity.ok().build();
    }
}