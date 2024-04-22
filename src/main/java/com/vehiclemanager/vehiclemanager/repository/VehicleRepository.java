package com.vehiclemanager.vehiclemanager.repository;

import com.vehiclemanager.vehiclemanager.entities.User;
import com.vehiclemanager.vehiclemanager.entities.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    void deleteByUser(User user);

    Page<Vehicle> findByUserUserId(UUID userId, Pageable pageable);
}
