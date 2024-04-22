package com.vehiclemanager.vehiclemanager.repository;

import com.vehiclemanager.vehiclemanager.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
